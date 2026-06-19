package com.example.QuadShop.business;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.QuadShop.business.Mapper.ToDomain;
import com.example.QuadShop.controller.dto.Requests.AddMedia;
import com.example.QuadShop.controller.dto.Requests.UpdateMedia;
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
                    ObjectUtils.emptyMap()
            );

            MediaEntity media = new MediaEntity();
            media.setId(metaData.get("public_id").toString());
            media.setUrl(metaData.get("url").toString());
            media.setType("image");
            media.setProduct(product);

            mediaRepo.save(media);

            return ToDomain.Media(media);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }


    public Media uploadVideo(AddMedia request) {
        try {
            ProductEntity product = productsRepo.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            List<MediaEntity> videos = product.getMedia().stream()
                    .filter(m -> "video".equalsIgnoreCase(m.getType()))
                    .toList();

            for (MediaEntity video : videos) {
                try {
                    cloudinary.uploader().destroy(
                            video.getId(),
                            ObjectUtils.asMap("resource_type", "video")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaRepo.delete(video);
            }

            product.getMedia().removeIf(m -> "video".equalsIgnoreCase(m.getType()));
            productsRepo.save(product);

            Map metaData = cloudinary.uploader().upload(
                    request.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "video")
            );

            String url = metaData.get("url").toString().replace("http://", "https://");

            MediaEntity media = new MediaEntity();
            media.setId(metaData.get("public_id").toString());
            media.setUrl(url);
            media.setType("video");
            media.setProduct(product);

            mediaRepo.save(media);

            return ToDomain.Media(media);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload video", e);
        }
    }

    public void delete(String id) throws Exception {
        System.out.println("delete " + id);

        MediaEntity media = mediaRepo.findById(id).orElse(null);
        if (media == null) return;

        ProductEntity product = media.getProduct();

        product.getMedia().remove(media);
        productsRepo.save(product);

        Map result = cloudinary.uploader().destroy(
                id,
                ObjectUtils.asMap("resource_type", media.getType())
        );
        mediaRepo.delete(media);

    }



    public void setDefault(String id) {
        MediaEntity media = mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        ProductEntity product = media.getProduct();
        if (product == null) {
            throw new RuntimeException("Media has no associated product");
        }

        product.setDefault_image(media);

        productsRepo.save(product);
    }

    public Media updateImageMetadata(String id, UpdateMedia request) {
        Optional<MediaEntity> media = mediaRepo.findById(id);
        if(media.isEmpty()){throw new IllegalStateException("Media not found");}
        MediaEntity mediaEntity = media.get();

        System.out.println(request.toString());

        mediaEntity.setLogo_type(request.getLogo_type());
        mediaEntity.setColour(request.getColour());

        mediaRepo.save(mediaEntity);

        ProductEntity product = mediaEntity.getProduct();
        if (request.getLogo_type()!=null) {
        product.setHas_logo_variant(true);
        productsRepo.save(product);}


        return ToDomain.Media(mediaEntity);

    }
}