package com.example.QuadShop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
    Long id;
    String size;
    String colour;
    long quantity;
}
