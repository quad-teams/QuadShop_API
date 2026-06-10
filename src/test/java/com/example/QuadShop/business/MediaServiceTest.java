//package com.example.QuadShop.business;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.Uploader;
//import com.example.QuadShop.controller.dto.Requests.AddMedia;
//import com.example.QuadShop.domain.Media;
//import com.example.QuadShop.domain.entity.MediaEntity;
//import com.example.QuadShop.domain.entity.ProductEntity;
//import com.example.QuadShop.persistence.MediaRepo;
//import com.example.QuadShop.persistence.ProductsRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.*;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MediaServiceTest {
//
//    @Mock private Cloudinary cloudinary;
//    @Mock private Uploader uploader;
//    @Mock private MediaRepo mediaRepo;
//    @Mock private ProductsRepo productsRepo;
//    @Mock private MultipartFile multipartFile;
//
//    @InjectMocks
//    private MediaService mediaService;
//
//    private ProductEntity product;
//    private AddMedia request;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        product = new ProductEntity();
//        product.setId(1L);
//        product.setMedia(new ArrayList<>());
//
//        request = new AddMedia();
//        request.setProductId(1L);
//        request.setFile(multipartFile);
//
//        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});
//        when(cloudinary.uploader()).thenReturn(uploader);
//    }
//
//    // ─────────────────────────────────────────────
//    // uploadImage
//    // ─────────────────────────────────────────────
//
//    @Test
//    void uploadImage_happyPath_returnsMedia() throws Exception {
//        Map<String, Object> cloudinaryResponse = Map.of(
//                "public_id", "img_abc123",
//                "url", "http://res.cloudinary.com/demo/image/upload/img_abc123"
//        );
//
//        when(productsRepo.findById(1L)).thenReturn(Optional.of(product));
//        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(cloudinaryResponse);
//
//        MediaEntity saved = new MediaEntity();
//        saved.setId("img_abc123");
//        saved.setUrl("http://res.cloudinary.com/demo/image/upload/img_abc123");
//        saved.setType("image");
//        when(mediaRepo.save(any(MediaEntity.class))).thenReturn(saved);
//
//        Media result = mediaService.uploadImage(request);
//
//        assert(result.getId()).equals("img_abc123");
//        assert(result.getType()).equals("image");
//        assert(result.getUrl()).contains("img_abc123");
//
//        verify(uploader).upload(any(byte[].class), anyMap());
//        verify(mediaRepo).save(any(MediaEntity.class));
//    }
//
//    @Test
//    void uploadImage_productNotFound_throwsRuntimeException() {
//        when(productsRepo.findById(1L)).thenReturn(Optional.empty());
//        assert (mediaService.uploadImage(request)).getType().equals(RuntimeException.class);
//    }
//
//    @Test
//    void uploadImage_cloudinaryThrows_throwsRuntimeException() throws Exception {
//        when(productsRepo.findById(1L)).thenReturn(Optional.of(product));
//        when(uploader.upload(any(byte[].class), anyMap())).thenThrow(new RuntimeException("Cloudinary down"));
//
//        assertThatThrownBy(() -> mediaService.uploadImage(request))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("Failed to upload image");
//    }
//
//    // ─────────────────────────────────────────────
//    // uploadVideo
//    // ─────────────────────────────────────────────
//
//    @Test
//    void uploadVideo_noExistingVideo_uploadsAndReturnsMedia() throws Exception {
//        Map<String, Object> cloudinaryResponse = Map.of(
//                "public_id", "vid_xyz789",
//                "url", "https://res.cloudinary.com/demo/video/upload/vid_xyz789"
//        );
//
//        when(productsRepo.findById(1L)).thenReturn(Optional.of(product));
//        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(cloudinaryResponse);
//
//        MediaEntity saved = new MediaEntity();
//        saved.setId("vid_xyz789");
//        saved.setUrl("https://res.cloudinary.com/demo/video/upload/vid_xyz789");
//        saved.setType("video");
//        when(mediaRepo.save(any(MediaEntity.class))).thenReturn(saved);
//
//        Media result = mediaService.uploadVideo(request);
//
//        assertThat(result.getId()).isEqualTo("vid_xyz789");
//        assertThat(result.getType()).isEqualTo("video");
//        assertThat(result.getUrl()).startsWith("https://");
//
//        verify(uploader).upload(any(byte[].class), anyMap());
//        verify(mediaRepo).save(any(MediaEntity.class));
//    }
//
//
//    @Test
//    void uploadVideo_httpUrlIsReplacedWithHttps() throws Exception {
//        Map<String, Object> cloudinaryResponse = Map.of(
//                "public_id", "vid_http_test",
//                "url", "http://res.cloudinary.com/demo/video/upload/vid_http_test"
//        );
//
//        when(productsRepo.findById(1L)).thenReturn(Optional.of(product));
//        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(cloudinaryResponse);
//
//        Media result = mediaService.uploadVideo(request);
//
//        assertThat(result.getUrl()).startsWith("https://");
//    }
//
//    @Test
//    void uploadVideo_productNotFound_throwsRuntimeException() {
//        when(productsRepo.findById(1L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> mediaService.uploadVideo(request))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("Failed to upload video");
//    }
//
//    @Test
//    void uploadVideo_cloudinaryDestroyFails_continuesAndUploadsNew() throws Exception {
//        MediaEntity existingVideo = new MediaEntity();
//        existingVideo.setId("old_vid_fail");
//        existingVideo.setType("video");
//        product.setMedia(new ArrayList<>(List.of(existingVideo)));
//
//        when(productsRepo.findById(1L)).thenReturn(Optional.of(product));
//        when(uploader.destroy(eq("old_vid_fail"), anyMap())).thenThrow(new RuntimeException("Destroy failed"));
//
//        Map<String, Object> cloudinaryResponse = Map.of(
//                "public_id", "vid_new_after_fail",
//                "url", "https://res.cloudinary.com/demo/video/upload/vid_new_after_fail"
//        );
//        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(cloudinaryResponse);
//
//        // Should not throw — destroy failure is caught internally
//        Media result = mediaService.uploadVideo(request);
//
//        assertThat(result.getId()).isEqualTo("vid_new_after_fail");
//        verify(mediaRepo).delete(existingVideo);
//    }
//
//    // ─────────────────────────────────────────────
//    // delete
//    // ─────────────────────────────────────────────
//
//    @Test
//    void delete_mediaNotFound_doesNothing() throws Exception {
//        when(mediaRepo.findById("nonexistent")).thenReturn(Optional.empty());
//
//        mediaService.delete("nonexistent");
//
//        verifyNoInteractions(productsRepo, uploader);
//        verify(mediaRepo, never()).delete(any());
//    }
//
//    // ─────────────────────────────────────────────
//    // setDefault
//    // ─────────────────────────────────────────────
//
//    @Test
//    void setDefault_validMedia_setsDefaultImageOnProduct() {
//        MediaEntity media = new MediaEntity();
//        media.setId("img_default");
//        media.setProduct(product);
//
//        when(mediaRepo.findById("img_default")).thenReturn(Optional.of(media));
//
//        mediaService.setDefault("img_default");
//
//        verify(productsRepo).save(product);
//        assertThat(product.getDefault_image()).isEqualTo(media);
//    }
//
//    @Test
//    void setDefault_mediaNotFound_throwsRuntimeException() {
//        when(mediaRepo.findById("missing")).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> mediaService.setDefault("missing"))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("Media not found");
//    }
//
//    @Test
//    void setDefault_mediaWithNoProduct_throwsRuntimeException() {
//        MediaEntity media = new MediaEntity();
//        media.setId("img_orphan");
//        media.setProduct(null);
//
//        when(mediaRepo.findById("img_orphan")).thenReturn(Optional.of(media));
//
//        assertThatThrownBy(() -> mediaService.setDefault("img_orphan"))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("no associated product");
//    }
//}