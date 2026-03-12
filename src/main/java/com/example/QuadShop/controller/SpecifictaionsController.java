package com.example.QuadShop.controller;

import com.example.QuadShop.business.SizesService;
import com.example.QuadShop.business.SpecifictaionsService;
import com.example.QuadShop.controller.dto.Requests.AddSize;
import com.example.QuadShop.controller.dto.Requests.AddSpecification;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/specifications")
public class SpecifictaionsController {
    private final SpecifictaionsService service;

    @PostMapping
    public void Add(AddSpecification request) {
        service.add(request);
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable long id) {
       service.delete(id);
    }

}
