package com.example.QuadShop.controller;

import com.example.QuadShop.business.ImageService;
import com.example.QuadShop.business.SizesService;
import com.example.QuadShop.controller.dto.Requests.AddImage;
import com.example.QuadShop.controller.dto.Requests.AddSize;
import com.example.QuadShop.domain.Size;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/sizes")
public class SizesController {
    private final SizesService service;

    @PostMapping
    public Size addSize(@RequestBody AddSize request) {
        System.out.println("controller hit");
        System.out.println(request);
        return service.add(request);
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable long id) {
        System.out.println("controller hit");
        service.delete(id);
    }

}
