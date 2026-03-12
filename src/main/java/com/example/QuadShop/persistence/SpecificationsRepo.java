package com.example.QuadShop.persistence;

import com.example.QuadShop.persistence.entity.SizeEntity;
import com.example.QuadShop.persistence.entity.SpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationsRepo extends JpaRepository<SpecificationEntity, Long> {

}
