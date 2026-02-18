package com.example.QuadShop.controller;

import com.example.QuadShop.business.OrdersService;
import com.example.QuadShop.business.ProductsService;
import com.example.QuadShop.controller.dto.AddOrderItemRequest;
import com.example.QuadShop.domain.Order;
import com.example.QuadShop.domain.OrderItem;
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
    public Order GetOrder(@PathVariable long id) {
        return service.getCartByID(id);
    }

    @PostMapping
    public Long NewCart() {
        return service.CreateCart();
    }

    @PostMapping("/AddOrderItem")
    public void addOrderItem(@RequestBody AddOrderItemRequest request) {
        System.out.println("hahahahah  "+request);
        service.addOrderItem(request.cartId,
                request.productId,
                request.quantity,
                request.size);
    }

}
