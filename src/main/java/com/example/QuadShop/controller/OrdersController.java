package com.example.QuadShop.controller;

import com.example.QuadShop.business.OrdersService;
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
@RequestMapping("/Order")
public class OrdersController {
    private final OrdersService service;

    @GetMapping("/{id}" )
    public Product GetCart(@PathVariable long id) {
        return service.getCartByID(id);
    }

}
