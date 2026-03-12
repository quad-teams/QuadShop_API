package com.example.QuadShop.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class ImageEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long product_id;
;
}
