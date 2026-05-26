package com.example.QuadShop.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Product {
    Long id;
    String name;
    String description;
    BigDecimal price;
    String category;
    String sub_category;
    boolean has_variants;
    Media video;
    List<Media> images;
    List<Stock> stock;
    List<Specification> specifications;
    Media default_image;
    String supplier;
    String supplier_product_id;
    String link;
    String notes;

}




