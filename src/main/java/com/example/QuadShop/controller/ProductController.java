package com.example.QuadShop.controller;

import com.example.QuadShop.business.ProductsService;
import com.example.QuadShop.controller.dto.Requests.AddProduct;
import com.example.QuadShop.controller.dto.Requests.UpdateProduct;
import com.example.QuadShop.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductsService service;

    @GetMapping
    public List<Product> getAllProducts() {
        return service.GetAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductsByID(@PathVariable long id) {
        return service.GetProductById(id);
    }

    @PostMapping
    public long addProduct(@RequestBody AddProduct request) {
        return service.CreateProduct(request);
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody UpdateProduct request) {
        service.EditProduct(request);
    }

    @DeleteMapping("/{id}")
    public void DeleteProduct(@PathVariable  long id) {service.delete(id);
    }
}
