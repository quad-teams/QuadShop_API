package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.Product;
import com.example.QuadShop.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductsRepo extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByCategory(String category);
    List<ProductEntity> findAllByNameContains(String name);
}
