package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class UpdateStock {
    private Long id;
    private Long productId;
    private String size;
    private String colour;
    private int quantity;

}
