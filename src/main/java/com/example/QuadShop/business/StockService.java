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
    // READ SINGLE STOCK ENTRY
    // ---------------------------------------------------------
    public Stock getById(long id) {
        return toDomain(loadStock(id));
    }

    // ---------------------------------------------------------
    // UPDATE STOCK ENTRY
    // ---------------------------------------------------------
    public Stock update(UpdateStock request) {

        ProductEntity product = loadProduct(request.getProductId());
        StockEntity stock;

        if (!product.isHas_variants()) {
            // Single-variant product → always one stock entry
            stock = product.getStock().getFirst();
        } else {
            // Variant product → stockId must be provided
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

        if (exists) {
            throw new RuntimeException("This size/colour combination already exists");
        }

        stock.setSize(request.getSize());
        stock.setColour(request.getColour());
        stock.setQuantity(request.getQuantity());

        return toDomain(stockRepo.save(stock));
    }

    // ---------------------------------------------------------
    // DELETE STOCK ENTRY
    // ---------------------------------------------------------
    public void delete(long id) {
        if (!stockRepo.existsById(id)) {
            throw new RuntimeException("Stock not found");
        }
        stockRepo.deleteById(id);
    }

    // ---------------------------------------------------------
    // ADD NEW SIZE
    // ---------------------------------------------------------
    public List<Stock> addSize(long productId, String newSize) {
        ProductEntity product = loadProduct(productId);

        if (stockRepo.existsByProduct_IdAndSize(productId, newSize)) {
            throw new RuntimeException("Size already exists for this product");
        }

        // If product has no variants, clear all stock entries first
        if (!product.isHas_variants()) {
            clearAllStock(productId);
        }

        ensureVariantMode(product);

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
    // ---------------------------------------------------------
    // ADD NEW COLOUR
    // ---------------------------------------------------------
    public List<Stock> addColour(long productId, String newColour) {
        ProductEntity product = loadProduct(productId);

        if (stockRepo.existsByProduct_IdAndColour(productId, newColour)) {
            throw new RuntimeException("Colour already exists for this product");
        }

        // If product has no variants, clear all stock entries first
        if (!product.isHas_variants()) {
            clearAllStock(productId);
        }

        ensureVariantMode(product);

        List<String> sizes = stockRepo.findByProduct_Id(productId)
                .stream()
                .map(StockEntity::getSize)
                .distinct()
                .toList();

        if (sizes.isEmpty()) {
            sizes = List.of("Default");
        }

        // If all existing stock rows have a Default/null colour, they were placeholder
        // entries created when only sizes existed. Remove them so we don't end up with
        // a mix of real-colour and Default-colour rows for the same sizes.
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

        List<StockEntity> created = sizes.stream()
                .filter(size -> size != null && !"Default".equals(size)) // only real sizes
                .map(size -> {
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
        ProductEntity product = loadProduct(productId);

        List<StockEntity> toDelete = stockRepo.findByProduct_Id(productId)
                .stream()
                .filter(s -> size.equals(s.getSize()))
                .toList();

        if (toDelete.isEmpty()) {
            throw new RuntimeException("Size not found for this product");
        }

        stockRepo.deleteAll(toDelete);
        handleVariantCollapse(productId, product);
    }

    // ---------------------------------------------------------
    // REMOVE COLOUR
    // ---------------------------------------------------------
    public void removeColour(long productId, String colour) {
        ProductEntity product = loadProduct(productId);

        List<StockEntity> toDelete = stockRepo.findByProduct_Id(productId)
                .stream()
                .filter(s -> colour.equals(s.getColour()))
                .toList();

        if (toDelete.isEmpty()) {
            throw new RuntimeException("Colour not found for this product");
        }

        stockRepo.deleteAll(toDelete);
        handleVariantCollapse(productId, product);
    }


    // ---------------------------------------------------------
    // PRIVATE HELPERS
    // ---------------------------------------------------------

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

    private void ensureVariantMode(ProductEntity product) {
        if (!product.isHas_variants()) {
            product.setHas_variants(true);
            productsRepo.save(product);
        }
    }

    private void handleVariantCollapse(long productId, ProductEntity product) {
        boolean hasRealVariants = stockRepo.findByProduct_Id(productId)
                .stream()
                .anyMatch(s -> !"Default".equals(s.getSize()) || !"Default".equals(s.getColour()));

        if (!hasRealVariants) {
            product.setHas_variants(false);
            productsRepo.save(product);
            seedDefaultEntry(product);
        }
    }

    private StockEntity seedDefaultEntry(ProductEntity product) {
        StockEntity s = new StockEntity();
        s.setProduct(product);
        s.setSize("Default");
        s.setColour("Default");
        s.setQuantity(0);
        return stockRepo.save(s);
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
