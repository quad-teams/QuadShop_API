package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItemEntity, Long> {

}
