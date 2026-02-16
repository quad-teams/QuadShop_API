package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.Order;
import com.example.QuadShop.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByStatusAndId(String status, Long id);
}
