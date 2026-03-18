package com.example.QuadShop.controller;

import com.example.QuadShop.business.ImageService;
import com.example.QuadShop.business.OrdersService;
import com.example.QuadShop.controller.dto.Requests.AddImage;
import com.example.QuadShop.controller.dto.Requests.AddOrderItem;
import com.example.QuadShop.domain.Image;
import com.example.QuadShop.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/images")
public class ImagesController {
    private final ImageService service;

    @PostMapping
    public Image AddImage(AddImage request) {
        return service.upload(request);
    }

    @DeleteMapping("/{id}")
    public void DeleteImage(@PathVariable String id) throws Exception {
       service.deleteImage(id);
    }

    @PutMapping("/{id}")
    public void EditImage(@PathVariable String id)  {
        service.setDefault(id);
    }
}
