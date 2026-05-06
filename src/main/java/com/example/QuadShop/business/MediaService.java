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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MediaService {

    private final Cloudinary cloudinary;
    private final MediaRepo mediaRepo;
    private final ProductsRepo productsRepo;

    public Media uploadImage(AddMedia request) {
        try {
            ProductEntity product = productsRepo.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            Map metaData = cloudinary.uploader().upload(
                    request.getFile().getBytes(),
                    ObjectUtils.emptyMap() // image defaults
            );

            MediaEntity media = new MediaEntity();
            media.setId(metaData.get("public_id").toString());
            media.setUrl(metaData.get("url").toString());
            media.setType("image");
            media.setProduct(product);

            mediaRepo.save(media);

            return new Media(media.getId(), media.getUrl(), media.getType());

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }


    public Media uploadVideo(AddMedia request) {
        try {
            ProductEntity product = productsRepo.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            // Remove existing video(s)
            List<MediaEntity> videos = product.getMedia().stream()
                    .filter(m -> "video".equalsIgnoreCase(m.getType()))
                    .toList();

            for (MediaEntity video : videos) {
                try {
                    // Delete from Cloudinary
                    cloudinary.uploader().destroy(
                            video.getId(),
                            ObjectUtils.asMap("resource_type", "video")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Delete from DB
                mediaRepo.delete(video);
            }

            // Remove from product list (required for orphanRemoval)
            product.getMedia().removeIf(m -> "video".equalsIgnoreCase(m.getType()));

            // Save product to update relationship
            productsRepo.save(product);

            // -----------------------------
            // NOW upload the new video
            // -----------------------------
            Map metaData = cloudinary.uploader().upload(
                    request.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "video")
            );

            MediaEntity media = new MediaEntity();
            media.setId(metaData.get("public_id").toString());
            media.setUrl(metaData.get("url").toString());
            media.setType("video");
            media.setProduct(product);

            mediaRepo.save(media);

            return new Media(media.getId(), media.getUrl(), media.getType());

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload video", e);
        }
    }

    public void delete(String id) throws Exception {
        System.out.println("delete " + id);

        MediaEntity media = mediaRepo.findById(id).orElse(null);
        if (media == null) return;

        ProductEntity product = media.getProduct();

        // 1. Remove from product list (required for orphanRemoval)
        product.getMedia().remove(media);
        productsRepo.save(product);

        // 2. Delete from Cloudinary
        Map result = cloudinary.uploader().destroy(
                id,
                ObjectUtils.asMap("resource_type", media.getType())
        );
        System.out.println("Cloudinary delete result: " + result);

        // 3. Delete from DB (optional, orphanRemoval already handles it)
        mediaRepo.delete(media);
        System.out.println("Media list after delete: " + product.getMedia().size());

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