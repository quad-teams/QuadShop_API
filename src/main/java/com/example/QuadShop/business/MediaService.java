package com.example.QuadShop.business;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.QuadShop.controller.dto.Requests.AddMedia;
import com.example.QuadShop.domain.Media;
import com.example.QuadShop.persistence.MediaRepo;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.domain.entity.MediaEntity;
import com.example.QuadShop.domain.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MediaService {

    private final Cloudinary cloudinary;
    private final MediaRepo mediaRepo;
    private final ProductsRepo productsRepo;

    public Media upload(AddMedia request) {
        try {
            ProductEntity product = productsRepo.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            // If uploading a video, remove existing video
            if ("video".equalsIgnoreCase(request.getType())) {
                product.getMedia().stream()
                        .filter(m -> "video".equals(m.getType()))
                        .forEach(m -> {
                            try {
                                delete(m.getId());
                            } catch (Exception ignored) {}
                        });
            }

            // Choose Cloudinary resource type
            Map options = "video".equalsIgnoreCase(request.getType())
                    ? ObjectUtils.asMap("resource_type", "video")
                    : ObjectUtils.emptyMap();

            // Upload to Cloudinary
            Map metaData = cloudinary.uploader().upload(
                    request.getFile().getBytes(),
                    options
            );

            // Save media
            MediaEntity media = new MediaEntity();
            media.setId(metaData.get("public_id").toString());
            media.setUrl(metaData.get("url").toString());
            media.setType(request.getType().toLowerCase());
            media.setProduct(product);

            mediaRepo.save(media);

            return new Media(media.getId(), media.getUrl(), media.getType());

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload media", e);
        }
    }

    public void delete(String id) throws Exception {
        Optional<MediaEntity> mediaEntity = mediaRepo.findById(id);
        if (mediaEntity.isEmpty()) return;

        mediaRepo.delete(mediaEntity.get());

        cloudinary.uploader().destroy(
                id,
                ObjectUtils.asMap("resource_type", mediaEntity.get().getType())
        );
    }

    public void setDefault(String id) {
        MediaEntity media = mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        ProductEntity product = media.getProduct();
        if (product == null) {
            throw new RuntimeException("Media has no associated product");
        }

        // Set the new default image
        product.setDefault_image(media);

        // Save the product, NOT the media
        productsRepo.save(product);
    }

}