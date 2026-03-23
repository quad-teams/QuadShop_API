package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepo extends JpaRepository<ImageEntity, String> {
}