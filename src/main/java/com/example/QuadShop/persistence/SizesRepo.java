package com.example.QuadShop.persistence;

import com.example.QuadShop.domain.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizesRepo extends JpaRepository<SizeEntity, Long> {

}
