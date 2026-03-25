package com.example.QuadShop.business.Mapper;

import com.example.QuadShop.domain.*;
import com.example.QuadShop.domain.entity.*;

import java.util.ArrayList;
import java.util.List;

public class ToDomain {

    // ProductEntity → Product
    public static Product Product(ProductEntity entity) {
        if (entity == null) return null;

        // Convert images
        List<Image> images = new ArrayList<>();
        if (entity.getImages() != null) {
            for (ImageEntity ie : entity.getImages()) {
                images.add(new Image(
                        ie.getId(),
                        ie.getUrl()
                ));
            }
        }

        // Convert default image
        Image defaultImage = Image(entity.getDefaultImage());

        // Push default image to the front
        if (defaultImage != null) {
            images.removeIf(img -> img.getId().equals(defaultImage.getId()));
            images.addFirst(defaultImage);
        }

        // Convert sizes
        List<Size> sizes = new ArrayList<>();
        if (entity.getSizes() != null) {
            for (SizeEntity se : entity.getSizes()) {
                sizes.add(new Size(
                        se.getId(),
                        se.getName()
                ));
            }
        }

        // Convert specifications
        List<Specification> specifications = new ArrayList<>();
        if (entity.getSpecifications() != null) {
            for (SpecificationEntity se : entity.getSpecifications()) {
                specifications.add(new Specification(
                        se.getId(),
                        se.getKey(),
                        se.getValue()
                ));
            }
        }

        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCategory(),
                entity.getSubCategory(),
                defaultImage,
                images,
                sizes,
                specifications
        );
    }


    public static Order Order(OrderEntity entity) {
        if (entity == null) return null;

        List<OrderItem> orderItems = new ArrayList<>();
        if (entity.getOrderItems() != null) {
            for (OrderItemEntity itemEntity : entity.getOrderItems()) {
                orderItems.add(OrderItem(itemEntity));
            }
        }

        return new Order(
                entity.getId(),
                entity.getStatus(),
                orderItems
        );
    }

    private static OrderItem OrderItem(OrderItemEntity entity) {
        if (entity == null) return null;
        return new OrderItem(
                entity.getId(),
                Product(entity.getProduct()),
                entity.getQuantity(),
                entity.getSize()
        );
    }

    private static Image Image(ImageEntity entity) {
        if (entity == null) return null;

        return new Image(entity.getId(), entity.getUrl());
    }

}
