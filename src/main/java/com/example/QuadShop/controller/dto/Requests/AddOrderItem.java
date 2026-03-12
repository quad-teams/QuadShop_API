package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class AddOrderItem {
    public Long cartId;
    public Long productId;
    public int quantity;
    public String size;
}