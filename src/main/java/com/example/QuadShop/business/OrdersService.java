package com.example.QuadShop.business;

import com.example.QuadShop.business.Mapper.ToDomain;
import com.example.QuadShop.controller.dto.Requests.UpdateOrder;
import com.example.QuadShop.controller.dto.Requests.UpdateOrderItem;
import com.example.QuadShop.domain.Order;
import com.example.QuadShop.persistence.OrderItemsRepo;
import com.example.QuadShop.persistence.OrdersRepo;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.domain.entity.OrderEntity;
import com.example.QuadShop.domain.entity.OrderItemEntity;
import com.example.QuadShop.domain.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrdersService {
    private final ProductsRepo productsRepo;
    private final OrdersRepo ordersRepo;
    private final OrderItemsRepo orderItemsRepo;



    public Order getByID(long id) {
        Optional<OrderEntity> entity= ordersRepo.findByStatusAndId("InCart",id);
        return entity.map(ToDomain::Order).orElse(null);
    }


    public Long createOrder(){
        OrderEntity NewCart = new OrderEntity();
        NewCart.setStatus("InCart");
        OrderEntity cart = ordersRepo.save(NewCart);
        return cart.getId();
    }

    public void UpdateOrder(UpdateOrder request) {
        OrderEntity order= ordersRepo.findById(request.id).orElse(null);
        if (order == null) return;
        order.setStatus(request.status);
        ordersRepo.save(order);
    }

    public void Checkout(Long id) {

    }

    // -------------------------------------------------------------------------
    // -------------------------- ORDER ITEMS  ---------------------------------
    // -------------------------------------------------------------------------



    public void addOrderItem(Long cartId, Long productId, int quantity, String size) {
        OrderEntity cart = ordersRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        ProductEntity product = productsRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setSize(size);

        orderItemsRepo.save(orderItem);
    }


    public void updateOrderItem(Long id, UpdateOrderItem request) {

        OrderItemEntity orderItem = orderItemsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        orderItem.setQuantity(request.quantity);
        orderItem.setSize(request.size);

        orderItemsRepo.save(orderItem);
    }

    public void deleteOrderItem(Long orderItemId) {
        orderItemsRepo.deleteById(orderItemId);
    }
}
