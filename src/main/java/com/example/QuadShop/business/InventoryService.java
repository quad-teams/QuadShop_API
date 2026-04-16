package com.example.QuadShop.business;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.QuadShop.controller.dto.Requests.AddMedia;
import com.example.QuadShop.domain.Media;
import com.example.QuadShop.domain.entity.MediaEntity;
import com.example.QuadShop.domain.entity.ProductEntity;
import com.example.QuadShop.persistence.MediaRepo;
import com.example.QuadShop.persistence.ProductsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {

    private final MediaRepo mediaRepo;
    private final ProductsRepo productsRepo;


}