package com.example.QuadShop.controller;

import com.example.QuadShop.business.MediaService;
import com.example.QuadShop.controller.dto.Requests.AddMedia;
import com.example.QuadShop.domain.Media;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/images")
public class MediaController {
    private final MediaService service;

    @PostMapping
    public Media AddMedia(AddMedia request) {
        return service.upload(request);
    }

    @DeleteMapping("/{id}")
    public void DeleteMedia(@PathVariable String id) throws Exception {
       service.delete(id);
    }
}
