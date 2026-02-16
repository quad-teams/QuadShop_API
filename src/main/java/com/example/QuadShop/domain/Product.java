package com.example.QuadShop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.List;

@Data
@AllArgsConstructor
public class Product {
    Long id;
    String name;
    String description;
    BigDecimal price;
    List<Image> images;
    List<Size> sizes;

}
