package com.example.QuadShop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    Long id;
    String Status;
    List<OrderItem> OrderItems;

}
