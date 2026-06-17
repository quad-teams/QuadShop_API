package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class item {
    long productId;
    int quantity;
    String size;
    String colour;
}
