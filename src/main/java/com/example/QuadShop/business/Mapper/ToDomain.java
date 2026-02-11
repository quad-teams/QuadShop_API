package com.example.QuadShop.business.Mapper;

import com.example.QuadShop.domain.*;
import com.example.QuadShop.persistence.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToDomain {

    // ProductEntity → Product
    public static Product toProduct(ProductEntity entity) {
        if (entity == null) return null;

        List<Specification> specifications = new ArrayList<>();
        if (entity.getSpecifications() != null) {
            for (SpecificationEntity se : entity.getSpecifications()) {
                specifications.add(toSpecification(se));
            }
        }

        List<Image> images = new ArrayList<>();
        if (entity.getImages() != null) {
            for (ImageEntity ie : entity.getImages()) {
                images.add(toImage(ie));
            }
        }

        List<Size> sizes = new ArrayList<>();
        if (entity.getSizes() != null) {
            for (SizeEntity se : entity.getSizes()) {
                sizes.add(toSize(se));
            }
        }

        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                specifications,
                images,
                sizes
        );
    }

    // SizeEntity → Size
    private static Size toSize(SizeEntity entity) {
        if (entity == null) return null;
        return new Size(
                entity.getId(),
                entity.getName()
        );
    }

    // ImageEntity → Image
    private static Image toImage(ImageEntity entity) {
        if (entity == null) return null;
        return new Image(
                entity.getId(),
                entity.getUrl()
        );
    }

    // SpecificationEntity → Specification
    private static Specification toSpecification(SpecificationEntity entity) {
        if (entity == null) return null;
        return new Specification(
                entity.getId(),
                entity.getKey(),
                entity.getValue()
        );
    }

    public static Order ToOrder(OrderEntity entity) {
        if (entity == null) return null;

        List<OrderItem> orderItems = new ArrayList<>();
        if (entity.getOrderItems() != null) {
            for (OrderItemEntity itemEntity : entity.getOrderItems()) {
                orderItems.add(toOrderItem(itemEntity));
            }
        }

        return new Order(
                entity.getId(),
                entity.getStatus(),
                orderItems
        );
    }

    private static OrderItem toOrderItem(OrderItemEntity entity) {
        if (entity == null) return null;
        return new OrderItem(
                entity.getId(),
                toProduct(entity.getProduct()),
                entity.getQuantity()
        );
    }

}
