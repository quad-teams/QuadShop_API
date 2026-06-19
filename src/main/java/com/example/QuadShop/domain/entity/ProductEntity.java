package com.example.QuadShop.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = true)
    private String subCategory;

    @Column(nullable = true)
    private String supplier_product_id;

    @Column(nullable = true)
    private String link;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = true)
    private boolean has_logo_variant;

    @Column(nullable = true)
    private String supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<StockEntity> stock = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<MediaEntity> media = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "default_image_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MediaEntity default_image;

}
