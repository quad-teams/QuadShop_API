package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

@Data
public class AddSpecification {
    public long ProductId;
    public String key;
    public String value;
}