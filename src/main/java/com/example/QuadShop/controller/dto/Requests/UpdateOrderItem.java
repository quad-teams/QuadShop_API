package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class UpdateOrderItem {
    public int quantity;
    public String size;
}