package com.example.QuadShop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationsRepo extends JpaRepository<SpecificationEntity, Long> {

}
