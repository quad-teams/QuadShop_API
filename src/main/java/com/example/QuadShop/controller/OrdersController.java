package com.example.QuadShop.controller;

import com.example.QuadShop.business.OrdersService;
import com.example.QuadShop.controller.dto.Requests.AddOrder;
import com.example.QuadShop.controller.dto.Requests.AddOrderItem;
import com.example.QuadShop.controller.dto.Requests.UpdateOrder;
import com.example.QuadShop.controller.dto.Requests.UpdateOrderItem;
import com.example.QuadShop.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService service;

    @GetMapping("" )
    public List<Order> GetAll() {
        return service.GetAllOrders();
    }
    @GetMapping("/{id}" )
    public Order GetOrder(@PathVariable long id) {

        return service.getByID(id);
    }

    @PutMapping("/{id}" )
    public void UpdateStatus(@RequestBody UpdateOrder request) {
        service.ChangeOrderStatus(request.id, request.status);
    }

    @PostMapping
    public Long NewOrder(@RequestBody AddOrder request) {
        return service.createOrder(request);
    }
}
