package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProduct {
    public String name ;
    public String category;
    public String description;
    public BigDecimal price;
}