package com.example.QuadShop.persistence;

import com.example.QuadShop.persistence.entity.ImageEntity;
import com.example.QuadShop.persistence.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizesRepo extends JpaRepository<SizeEntity, Long> {

}
