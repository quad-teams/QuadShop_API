package com.example.QuadShop.business;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.QuadShop.controller.dto.Requests.AddImage;
import com.example.QuadShop.domain.Image;
import com.example.QuadShop.persistence.ImagesRepo;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.persistence.entity.ImageEntity;
import com.example.QuadShop.persistence.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ImageService {

    private final Cloudinary cloudinary;
    private final ImagesRepo imagesRepo;
    private final ProductsRepo productsRepo;


    public Image upload(AddImage request) {
        try {
            Optional<ProductEntity> productEntity = productsRepo.findById(request.ProductId);

            if (productEntity.isEmpty()) {
                throw new IllegalStateException("product not found");
            }

            Map metaData = cloudinary.uploader().upload(request.Image.getBytes(), ObjectUtils.emptyMap());
            ImageEntity image = new ImageEntity();
            image.setId(metaData.get("public_id").toString());
            image.setUrl(metaData.get("url").toString());
            image.setProduct_id(request.ProductId);

            imagesRepo.save(image);

            return new Image(image.getId(), image.getUrl());

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public void deleteImage(String publicId) throws Exception {
        Optional<ImageEntity> imageEntity =  imagesRepo.findById(publicId);
        if (imageEntity.isEmpty()) { return;}
        imagesRepo.delete(imageEntity.get());
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
