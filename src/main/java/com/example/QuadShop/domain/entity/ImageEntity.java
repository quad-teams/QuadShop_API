package com.example.QuadShop.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class ImageEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductEntity product;
    ;
}
