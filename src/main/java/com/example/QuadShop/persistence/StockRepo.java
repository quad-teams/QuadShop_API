package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<StockEntity, Long> {


    List<StockEntity> findByProductId(long productId);

    boolean existsByProduct_IdAndColour(Long productId, String colour);

    boolean existsByProduct_IdAndColourAndSize(Long productId, String colour, String size);

    boolean existsByProduct_IdAndSize(Long productId, String size);

    List<StockEntity> findByProduct_Id(long productId);

    List<StockEntity> findByProduct_Id(Long productId);

    List<StockEntity> findAllByProduct_Id(Long productId);
}
