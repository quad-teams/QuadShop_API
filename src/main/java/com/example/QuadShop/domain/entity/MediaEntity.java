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
public class MediaEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String type;

    // Many images belong to one product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductEntity product;

    // One image can be the default image of a product
    @OneToOne(mappedBy = "default_image")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductEntity productDefaultImage;
}
