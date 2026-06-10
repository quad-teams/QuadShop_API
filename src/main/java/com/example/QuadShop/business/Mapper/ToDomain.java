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
                        images.add(new Media(
                                EM.getId(),
                                EM.getUrl(),
                                EM.getType(),
                                EM.getColour()
                        ));
                    }
                }
                else if (EM.getType().equals("video")) {
                    video = new Media(EM.getId(),
                            EM.getUrl(),
                            EM.getType(),
                            EM.getColour());
                }
            }
        }

        Media default_image=null;



        if (entity.getDefault_image()!=null)  { default_image=      new Media(entity.getDefault_image().getId(),
                entity.getDefault_image().getUrl(),
                entity.getDefault_image().getType(),
                entity.getDefault_image().getColour());
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
                entity.isHas_variants(),
                video,
                images,
                stock,
                default_image,
                entity.getSupplier(),
                entity.getSupplier_product_id(),
                entity.getLink(),
                entity.getNotes()

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
                entity.getId(),
                entity.getQuantity(),
                entity.getSize(),
                entity.getColour()
        );
    }

}
