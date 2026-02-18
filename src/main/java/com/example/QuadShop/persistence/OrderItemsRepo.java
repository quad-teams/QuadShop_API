package com.example.QuadShop.persistence;

import com.example.QuadShop.persistence.entity.OrderEntity;
import com.example.QuadShop.persistence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItemEntity, Long> {

}
