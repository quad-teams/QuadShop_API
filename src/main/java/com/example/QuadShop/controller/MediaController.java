package com.example.QuadShop.controller;

import com.example.QuadShop.business.MediaService;
import com.example.QuadShop.controller.dto.Requests.AddMedia;
import com.example.QuadShop.domain.Media;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class MediaController {
    private final MediaService service;

    @PostMapping("/images")
    public Media addImage(@ModelAttribute AddMedia request) {
        request.setType("image");
        return service.uploadImage(request);
    }

    @PostMapping("/videos")
    public Media addVideo(@ModelAttribute AddMedia request) {
        request.setType("video");
        return service.uploadVideo(request);
    }

    @DeleteMapping("/images/{id}")
    public void deleteImage(@PathVariable String id) throws Exception {
        service.delete(id);
    }

    @DeleteMapping("/media/{id}")
    public void deleteMedia(@PathVariable String id) throws Exception {
        service.delete(id);
    }

    @PutMapping("/media/{id}/default")
    public void setDefaultImage(@PathVariable String id) {
        service.setDefault(id);
    }


}
