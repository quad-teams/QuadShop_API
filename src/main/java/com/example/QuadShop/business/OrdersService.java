package com.example.QuadShop.business;

import com.example.QuadShop.business.Mapper.ToDomain;
import com.example.QuadShop.controller.dto.Requests.AddOrder;
import com.example.QuadShop.controller.dto.Requests.UpdateOrder;
import com.example.QuadShop.controller.dto.Requests.UpdateOrderItem;
import com.example.QuadShop.domain.Order;
import com.example.QuadShop.domain.OrderItem;
import com.example.QuadShop.domain.Stock;
import com.example.QuadShop.domain.entity.StockEntity;
import com.example.QuadShop.persistence.OrderItemsRepo;
import com.example.QuadShop.persistence.OrdersRepo;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.domain.entity.OrderEntity;
import com.example.QuadShop.domain.entity.OrderItemEntity;
import com.example.QuadShop.domain.entity.ProductEntity;
import com.example.QuadShop.persistence.StockRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrdersService {
    private final ProductsRepo productsRepo;
    private final OrdersRepo ordersRepo;
    private final OrderItemsRepo orderItemsRepo;
    private final StockRepo stockrepo ;



    public Order getByID(long id) {
        Optional<OrderEntity> entity= ordersRepo.findByStatusAndId("InCart",id);
        return entity.map(ToDomain::Order).orElse(null);
    }


    public Long createOrder(AddOrder request) {
        OrderEntity order = new OrderEntity();
        order.setStatus("pending");
        order.setUser_email(request.getEmail());

        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItem i : request.getItems()) {
            // Normalize null/"" to "Default" to match DB convention
            String size   = (i.getSize()   == null || i.getSize().isBlank())   ? "Default" : i.getSize();
            String colour = (i.getColour() == null || i.getColour().isBlank()) ? "Default" : i.getColour();

            StockEntity stock = stockrepo.findByColourAndSizeAndProduct_Id(colour, size, i.getProductId());

            if (stock == null) {
                throw new IllegalArgumentException(
                        "No stock found for productId=" + i.getProductId()
                                + ", size=" + size + ", colour=" + colour
                );
            }

            stock.setQuantity(stock.getQuantity() - i.getQuantity());
            stockrepo.save(stock);

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setSize(size);
            orderItem.setColour(colour);
            orderItem.setQuantity(i.getQuantity());
            orderItem.setProduct(productsRepo.getReferenceById(i.getProductId()));
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        return ordersRepo.save(order).getId();
    }
}


