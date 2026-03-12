package com.example.QuadShop.controller;

import com.example.QuadShop.business.OrdersService;
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
    public Long NewCart() {
        return service.createOrder();
    }

    @PostMapping("/AddOrderItem")
    public void addOrderItem(@RequestBody AddOrderItem request) {
        System.out.println("hahahahah  "+request);
        service.addOrderItem(request.cartId,
                request.productId,
                request.quantity,
                request.size);
    }

    @PutMapping("/UpdateOrderItem/{id}")
    public void updateOrderItem(
            @PathVariable Long id,
            @RequestBody UpdateOrderItem request
    ) {
        service.updateOrderItem(id, request);
    }


}
