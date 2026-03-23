package com.example.QuadShop.business;

import com.example.QuadShop.controller.dto.Requests.AddSpecification;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.persistence.SpecificationsRepo;
import com.example.QuadShop.domain.entity.ProductEntity;
import com.example.QuadShop.domain.entity.SpecificationEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SpecifictaionsService {

    private final SpecificationsRepo specificationsRepo;
    private final ProductsRepo productsRepo;


    public void add(@RequestBody AddSpecification request) {
        Optional<ProductEntity> product = productsRepo.findById(request.getProductId());
        if (product.isEmpty()) return;

        SpecificationEntity  specificationEntity = new SpecificationEntity();
        specificationEntity.setKey(request.getKey());
        specificationEntity.setValue(request.getValue());
        specificationsRepo.save(specificationEntity);

    }

    public void delete(long id) {
        specificationsRepo.deleteById(id);
    }
}
