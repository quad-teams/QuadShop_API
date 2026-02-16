package com.example.QuadShop.controller;

import com.example.QuadShop.business.ProductsService;
import com.example.QuadShop.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductsService service;

    @GetMapping
    public List<Product> getAllProducts() {
        System.out.println(">>> Controller hit");        // add this
        return service.GetAllProducts();
    }

    @GetMapping(params = "category")
    public List<Product> getProductsByCategory(String category) {
        return service.GetProductsByCategory(category);
    }


    @GetMapping("/{id}")
    public Product getProductsByID(@PathVariable long id) {
        System.out.println(">>> Controller hit");
        return service.GetProductById(id);
    }

    @GetMapping(params = "search")
    public List<Product> getProductsByC(String search) {
        return service.SearchProducts(search);
    }


}
