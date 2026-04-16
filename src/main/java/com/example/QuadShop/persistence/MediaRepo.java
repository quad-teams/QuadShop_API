package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepo extends JpaRepository<MediaEntity, String> {
}