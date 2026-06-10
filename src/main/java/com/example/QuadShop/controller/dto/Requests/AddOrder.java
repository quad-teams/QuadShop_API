package com.example.QuadShop.controller.dto.Requests;

import com.example.QuadShop.domain.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class AddOrder {
    public String email;
    public String full_name;
    public List<OrderItem> items;
}