package com.example.QuadShop.business;

import com.example.QuadShop.controller.dto.Requests.AddStock;
import com.example.QuadShop.controller.dto.Requests.UpdateStock;
import com.example.QuadShop.domain.Stock;
import com.example.QuadShop.domain.entity.ProductEntity;
import com.example.QuadShop.domain.entity.StockEntity;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.persistence.StockRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepo stockRepo;
    private final ProductsRepo productsRepo;

    // ---------------------------------------------------------
    // CREATE SINGLE STOCK ENTRY
    // ---------------------------------------------------------
    public Stock add(AddStock request) {
        ProductEntity product = loadProduct(request.getProductId());

        if (stockRepo.existsByProduct_IdAndColourAndSize(
                request.getProductId(),
                request.getColour(),
                request.getSize()
        )) {
            throw new RuntimeException("This size/colour combination already exists");
        }

        StockEntity stock = new StockEntity();
        stock.setProduct(product);
        stock.setSize(request.getSize());
        stock.setColour(request.getColour());
        stock.setQuantity(request.getQuantity());

        return toDomain(stockRepo.save(stock));
    }


    // ---------------------------------------------------------
    // UPDATE STOCK ENTRY
    // ---------------------------------------------------------
    public Stock update(UpdateStock request) {
        ProductEntity product = loadProduct(request.getProductId());
        StockEntity stock;

        boolean hasVariants = hasRealVariants(request.getProductId());

        if (!hasVariants) {
            // No variants — always one Default/Default stock entry
            stock = product.getStock().getFirst();
        } else {
            // Variant product — stockId must be provided
            if (request.getId() == null) {
                throw new RuntimeException("Stock ID is required for variant products");
            }
            stock = loadStock(request.getId());
        }

        // Duplicate check
        boolean exists = stockRepo.existsByProduct_IdAndColourAndSizeAndIdNot(
                product.getId(),
                request.getColour(),
                request.getSize(),
                stock.getId()
        );

        if (product.getStock().size() > 1 && exists) {
            throw new RuntimeException("This size/colour combination already exists");
        }

        stock.setSize(request.getSize());
        stock.setColour(request.getColour());
        stock.setQuantity(request.getQuantity());

        return toDomain(stockRepo.save(stock));
    }

    // ---------------------------------------------------------
    // ADD NEW SIZE
    // ---------------------------------------------------------
    public List<Stock> addSize(long productId, String newSize) {
        ProductEntity product = loadProduct(productId);

        if (stockRepo.existsByProduct_IdAndSize(productId, newSize)) {
            throw new RuntimeException("Size already exists for this product");
        }

        // If no real variants exist yet, wipe the Default/Default placeholder
        if (!hasRealVariants(productId)) {
            clearAllStock(productId);
        }

        // Use existing colours, or fall back to Default
        List<String> colours = stockRepo.findByProduct_Id(productId)
                .stream()
                .map(StockEntity::getColour)
                .distinct()
                .toList();

        if (colours.isEmpty()) {
            colours = List.of("Default");
        }

        List<StockEntity> created = colours.stream().map(colour -> {
            StockEntity s = new StockEntity();
            s.setProduct(product);
            s.setSize(newSize);
            s.setColour(colour);
            s.setQuantity(0);
            return stockRepo.save(s);
        }).toList();

        return created.stream().map(this::toDomain).toList();
    }

    // ---------------------------------------------------------
    // ADD NEW COLOUR
    // ---------------------------------------------------------
    public List<Stock> addColour(long productId, String newColour) {
        ProductEntity product = loadProduct(productId);

        if (stockRepo.existsByProduct_IdAndColour(productId, newColour)) {
            throw new RuntimeException("Colour already exists for this product");
        }

        // If no real variants exist yet, wipe the Default/Default placeholder
        if (!hasRealVariants(productId)) {
            clearAllStock(productId);
        }

        // Capture sizes BEFORE deleting any Default-colour placeholder rows
        List<String> sizes = stockRepo.findByProduct_Id(productId)
                .stream()
                .map(StockEntity::getSize)
                .distinct()
                .toList();

        // If all existing rows have a Default/null colour they are placeholders
        // created when only sizes existed — remove them to avoid duplicates
        boolean onlyDefaultColour = stockRepo.findByProduct_Id(productId)
                .stream()
                .allMatch(s -> s.getColour() == null || "Default".equals(s.getColour()));

        if (onlyDefaultColour) {
            List<StockEntity> defaultColourEntries = stockRepo.findByProduct_Id(productId)
                    .stream()
                    .filter(s -> s.getColour() == null || "Default".equals(s.getColour()))
                    .toList();
            stockRepo.deleteAll(defaultColourEntries);
        }

        // If no real sizes exist, fall back to Default
        if (sizes.isEmpty()) {
            sizes = List.of("Default");
        }

        List<StockEntity> created = sizes.stream().map(size -> {
            StockEntity s = new StockEntity();
            s.setProduct(product);
            s.setSize(size);
            s.setColour(newColour);
            s.setQuantity(0);
            return stockRepo.save(s);
        }).toList();

        return created.stream().map(this::toDomain).toList();
    }

    // ---------------------------------------------------------
    // REMOVE SIZE
    // ---------------------------------------------------------
    public void removeSize(long productId, String size) {
        // If already collapsed to no-variant mode, nothing to do
        if (!hasRealVariants(productId)) {
            return;
        }

        List<StockEntity> toDelete = stockRepo.findByProduct_Id(productId)
                .stream()
                .filter(s -> size.equals(s.getSize()))
                .toList();

        if (toDelete.isEmpty()) {
            throw new RuntimeException("Size not found for this product");
        }

        stockRepo.deleteAll(toDelete);
        handleVariantCollapse(productId, loadProduct(productId));
    }

    // ---------------------------------------------------------
    // REMOVE COLOUR
    // ---------------------------------------------------------
    public void removeColour(long productId, String colour) {
        // If already collapsed to no-variant mode, nothing to do
        if (!hasRealVariants(productId)) {
            return;
        }

        List<StockEntity> toDelete = stockRepo.findByProduct_Id(productId)
                .stream()
                .filter(s -> colour.equals(s.getColour()))
                .toList();

        if (toDelete.isEmpty()) {
            throw new RuntimeException("Colour not found for this product");
        }

        stockRepo.deleteAll(toDelete);
        handleVariantCollapse(productId, loadProduct(productId));
    }

    // ---------------------------------------------------------
    // PRIVATE HELPERS
    // ---------------------------------------------------------

    /** Returns true if this product has any stock row that isn't Default/Default. */
    private boolean hasRealVariants(long productId) {
        return stockRepo.findByProduct_Id(productId)
                .stream()
                .anyMatch(s -> !"Default".equals(s.getSize()) || !"Default".equals(s.getColour()));
    }

    private ProductEntity loadProduct(long id) {
        return productsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private StockEntity loadStock(long id) {
        return stockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock entry not found"));
    }

    private void clearAllStock(long productId) {
        List<StockEntity> all = stockRepo.findByProduct_Id(productId);
        if (!all.isEmpty()) {
            stockRepo.deleteAll(all);
        }
    }

    private void handleVariantCollapse(long productId, ProductEntity product) {
        boolean hasRealVariants = hasRealVariants(productId);
        if (!hasRealVariants) {
            seedDefaultEntry(product);
        }
    }

    private void seedDefaultEntry(ProductEntity product) {
        StockEntity s = new StockEntity();
        s.setProduct(product);
        s.setSize("Default");
        s.setColour("Default");
        s.setQuantity(0);
        stockRepo.save(s);
    }

    private Stock toDomain(StockEntity e) {
        return new Stock(
                e.getId(),
                e.getSize(),
                e.getColour(),
                e.getQuantity()
        );
    }
}