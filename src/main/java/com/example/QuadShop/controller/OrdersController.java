package com.example.QuadShop.controller;

import com.example.QuadShop.business.OrdersService;
import com.example.QuadShop.controller.dto.Requests.AddOrder;
import com.example.QuadShop.controller.dto.Requests.AddOrderItem;
import com.example.QuadShop.controller.dto.Requests.UpdateOrderItem;
import com.example.QuadShop.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService service;

    @GetMapping("/{id}" )
    public Order GetOrder(@PathVariable long id) {
        System.out.println("getting cart");
        return service.getByID(id);
    }

    @PostMapping
    public Long NewOrder(@RequestBody AddOrder request) {
        return service.createOrder(request);
    }
}
