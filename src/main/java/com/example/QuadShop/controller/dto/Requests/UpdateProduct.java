package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProduct {
    public long id;
    public String name ;
    public String category;
    public String description;
    public BigDecimal price;
    public String sub_category;
    public String supplier;
    public String supplier_product_id;
    public String link;
    public String notes;
}