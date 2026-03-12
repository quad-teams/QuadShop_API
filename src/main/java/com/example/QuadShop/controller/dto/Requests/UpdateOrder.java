package com.example.QuadShop.controller.dto.Requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateOrder {
    public long id;
    public String status;
}