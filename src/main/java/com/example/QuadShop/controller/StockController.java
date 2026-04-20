package com.example.QuadShop.controller;

import com.example.QuadShop.business.StockService;
import com.example.QuadShop.controller.dto.Requests.AddStock;
import com.example.QuadShop.controller.dto.Requests.AddVariant;
import com.example.QuadShop.controller.dto.Requests.UpdateStock;
import com.example.QuadShop.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService service;

    // -----------------------------
    // CREATE
    // -----------------------------
    @PostMapping
    public Stock add(@RequestBody AddStock request) {
        return service.add(request);
    }

    // -----------------------------
    // UPDATE
    // -----------------------------
    @PutMapping
    public Stock update(@RequestBody UpdateStock request) {
        return service.update(request);
    }



    // -----------------------------
    // ADD SIZE
    // -----------------------------
    @PostMapping("/products/{productId}/sizes")
    public List<Stock> addSize(@PathVariable long productId, @RequestBody AddVariant request) {
        return service.addSize(productId, request.getValue());
    }

    // -----------------------------
    // ADD COLOUR
    // -----------------------------
    @PostMapping("/products/{productId}/colours")
    public List<Stock> addColour(@PathVariable long productId, @RequestBody AddVariant request) {
        return service.addColour(productId, request.getValue());
    }

    // -----------------------------
    // DELETE SIZE
    // -----------------------------
    @DeleteMapping("/products/{productId}/sizes/{size}")
    public void deleteSize(@PathVariable long productId, @PathVariable String size) {
        service.removeSize(productId, size);
    }

    // -----------------------------
    // DELETE COLOUR
    // -----------------------------
    @DeleteMapping("/products/{productId}/colours/{colour}")
    public void deleteColour(@PathVariable long productId, @PathVariable String colour) {
        service.removeColour(productId, colour);
    }
}
