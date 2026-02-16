package com.example.QuadShop.controller;

import com.example.QuadShop.business.OrdersService;
import com.example.QuadShop.business.ProductsService;
import com.example.QuadShop.domain.Order;
import com.example.QuadShop.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService service;

    @GetMapping("/{id}" )
    public Order GetCart(@PathVariable long id) {
        return service.getCartByID(id);
    }

    @PostMapping("/checkout")
    public void Checkout(@RequestBody Order order) {
        /// service.checkout()
    }

}
