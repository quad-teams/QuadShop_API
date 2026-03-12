package com.example.QuadShop.business;

import com.cloudinary.utils.ObjectUtils;
import com.example.QuadShop.controller.dto.Requests.AddSize;
import com.example.QuadShop.domain.Size;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.persistence.SizesRepo;
import com.example.QuadShop.persistence.entity.ImageEntity;
import com.example.QuadShop.persistence.entity.ProductEntity;
import com.example.QuadShop.persistence.entity.SizeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SizesService {

    private final SizesRepo sizesRepo;
    private final ProductsRepo productsRepo;


    public Size add(AddSize request) {
        Optional<ProductEntity> product = productsRepo.findById(request.getProductId());
        if (product.isEmpty()) throw new RuntimeException("product not found");

        SizeEntity size = new SizeEntity();
        size.setName(request.getSize());
        size.setProduct_id(request.getProductId());

        SizeEntity sizeSaved = sizesRepo.save(size);

        return new Size(sizeSaved.getId(), sizeSaved.getName());

    }

    public void delete(long id) {
        sizesRepo.deleteById(id);
    }
}
