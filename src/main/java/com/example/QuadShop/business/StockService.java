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
        ProductEntity product = productsRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Prevent duplicate size+colour combination
        if (stockRepo.existsByProduct_IdAndColourAndSize(
                request.getProductId(),
                request.getColour(),
                request.getSize()
        )) {
            throw new RuntimeException("This size/colour combination already exists");
        }

        StockEntity stock = new StockEntity();
        stock.setSize(request.getSize());
        stock.setColour(request.getColour());
        stock.setQuantity(0);
        stock.setProduct(product);

        StockEntity saved = stockRepo.save(stock);
        return toDomain(saved);
    }

    // ---------------------------------------------------------
    // READ SINGLE STOCK ENTRY
    // ---------------------------------------------------------
    public Stock getById(long id) {
        StockEntity stock = stockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        return toDomain(stock);
    }

    // ---------------------------------------------------------
    // UPDATE STOCK ENTRY
    // ---------------------------------------------------------
    public Stock update(long id, UpdateStock request) {
        StockEntity stock = stockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        // Prevent duplicate size+colour combination
        if (stockRepo.existsByProduct_IdAndColourAndSize(
                stock.getProduct().getId(),
                request.getSize(),
                request.getColour()
        )) {
            throw new RuntimeException("This size/colour combination already exists");
        }

        stock.setSize(request.getSize());
        stock.setColour(request.getColour());
        stock.setQuantity(request.getQuantity());

        StockEntity saved = stockRepo.save(stock);
        return toDomain(saved);
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
    // ADD NEW SIZE TO PRODUCT
    // ---------------------------------------------------------
    public List<Stock> addSize(long productId, String newSize) {

        ProductEntity product = productsRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if size already exists
        if (stockRepo.existsByProduct_IdAndSize(productId, newSize)) {
            throw new RuntimeException("Size already exists for this product");
        }

        // Get all existing colours
        List<StockEntity> existing = stockRepo.findByProduct_Id(productId);
        List<String> colours = existing.stream()
                .map(StockEntity::getColour)
                .distinct()
                .toList();

        // If no colours exist yet, create a default one
        if (colours.isEmpty()) {
            colours = List.of("Default");
        }

        // Create stock entries for each colour
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
    // ADD NEW COLOUR TO PRODUCT
    // ---------------------------------------------------------
    public List<Stock> addColour(long productId, String newColour) {

        ProductEntity product = productsRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if colour already exists
        if (stockRepo.existsByProduct_IdAndColour(productId, newColour)) {
            throw new RuntimeException("Colour already exists for this product");
        }

        // Get all existing sizes
        List<StockEntity> existing = stockRepo.findAllByProduct_Id(productId);
        List<String> sizes = existing.stream()
                .map(StockEntity::getSize)
                .distinct()
                .toList();

        // If no sizes exist yet, create a default one
        if (sizes.isEmpty()) {
            sizes = List.of("Default");
        }

        // Create stock entries for each size
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
    // MAPPING ENTITY → DOMAIN
    // ---------------------------------------------------------
    private Stock toDomain(StockEntity e) {
        return new Stock(
                e.getId(),
                e.getSize(),
                e.getColour(),
                e.getQuantity()
        );
    }
}
