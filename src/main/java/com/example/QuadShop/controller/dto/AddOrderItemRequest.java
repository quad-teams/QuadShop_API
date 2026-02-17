package com.example.QuadShop.controller.dto;

import lombok.Data;

@Data
public class AddOrderItemRequest {
    public Long cartId;
    public Long productId;
    public int quantity;
    public String size;
}