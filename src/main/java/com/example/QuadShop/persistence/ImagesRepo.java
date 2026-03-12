package com.example.QuadShop.persistence;

import com.example.QuadShop.persistence.entity.ImageEntity;
import com.example.QuadShop.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagesRepo extends JpaRepository<ImageEntity, String> {

}
