package com.example.QuadShop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    Long id;
    String full_Name;
    String email;
    String Status;
    List<OrderItem> OrderItems;
    LocalDateTime created_on;

}
