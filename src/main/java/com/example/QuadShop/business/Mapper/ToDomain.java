package com.example.QuadShop.business.Mapper;

import com.example.QuadShop.domain.*;
import com.example.QuadShop.domain.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDomain {

    // ProductEntity → Product
    public static Product Product(ProductEntity entity) {
        if (entity == null) return null;

        // Convert images
        Media video=null;
        List<Media> images = new ArrayList<>();
        if (entity.getMedia() != null) {
            for (MediaEntity EM : entity.getMedia()) {
                if (EM.getType().equals("image")) {
                    if (entity.getDefault_image()!=null && !Objects.equals(EM.getId(), entity.getDefault_image().getId())) {
                        images.add(Media(EM));
                    }
                }
                else if (EM.getType().equals("video")) {
                    video = Media(EM);
                }
            }
        }

        Media default_image=null;


        if (entity.getDefault_image()!=null)  { default_image=Media(entity.getDefault_image());
            images.addFirst(default_image);
        }

        // Convert sizes
        List<Stock> stock = new ArrayList<>();
        if (entity.getStock() != null) {
            for (StockEntity se : entity.getStock()) {
                stock.add(new Stock(
                        se.getId(),
                        se.getSize(),
                        se.getColour(),
                        se.getQuantity()
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
                video,
                images,
                stock,
                default_image,
                entity.getSupplier(),
                entity.getSupplier_product_id(),
                entity.getLink(),
                entity.getNotes(),
                entity.isHas_logo_variant()

        );
    }

    public static Media Media(MediaEntity entity) {
        return new Media(
                entity.getId(),
                entity.getUrl(),
                entity.getType(),
                entity.getColour(),
                entity.getLogo_type()
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
                entity.getFull_name(),
                entity.getUser_email(),
                entity.getStatus(),
                orderItems,
                entity.getCreated_on()
        );
    }

    private static OrderItem OrderItem(OrderItemEntity entity) {
        if (entity == null) return null;
        return new OrderItem(
                Product(entity.getProduct()),
                entity.getQuantity(),
                entity.getSize(),
                entity.getColour()
        );
    }

}
