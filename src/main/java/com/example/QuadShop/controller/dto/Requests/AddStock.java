package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class AddStock {
    public long productId;
    public String size;
    public String colour;
}