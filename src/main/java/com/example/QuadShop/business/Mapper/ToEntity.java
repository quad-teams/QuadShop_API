package com.example.QuadShop.business.Mapper;

import com.example.QuadShop.domain.Image;
import com.example.QuadShop.domain.Product;
import com.example.QuadShop.domain.Size;
import com.example.QuadShop.domain.Specification;
import com.example.QuadShop.persistence.entity.ImageEntity;
import com.example.QuadShop.persistence.entity.ProductEntity;
import com.example.QuadShop.persistence.entity.SizeEntity;
import com.example.QuadShop.persistence.entity.SpecificationEntity;

import java.util.ArrayList;
import java.util.List;

public class ToEntity {


    public static ProductEntity toProductEntity(Product product) {
        if (product == null) return null;

        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        return entity;
    }


}
