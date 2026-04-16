package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class UpdateStock {
    public long id;
    public String size;
    public String colour;
    public int quantity;
}