package com.example.QuadShop.business.Mapper;

import com.example.QuadShop.domain.*;
import com.example.QuadShop.persistence.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToDomain {

    // ProductEntity → Product
    public static Product Product(ProductEntity entity) {
        if (entity == null) return null;

        List<Image> images = new ArrayList<>();
        if (entity.getImages() != null) {
            for (ImageEntity ie : entity.getImages()) {
                images.add(Image(ie));
            }
        }

        List<Size> sizes = new ArrayList<>();
        if (entity.getSizes() != null) {
            for (SizeEntity se : entity.getSizes()) {
                sizes.add(Size(se));
            }
        }

        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                images,
                sizes
        );
    }

    // SizeEntity → Size
    private static Size Size(SizeEntity entity) {
        if (entity == null) return null;
        return new Size(
                entity.getId(),
                entity.getName()
        );
    }

    // ImageEntity → Image
    private static Image Image(ImageEntity entity) {
        if (entity == null) return null;
        return new Image(
                entity.getId(),
                entity.getUrl()
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

}
