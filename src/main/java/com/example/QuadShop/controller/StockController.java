package com.example.QuadShop.controller;

import com.example.QuadShop.business.StockService;
import com.example.QuadShop.controller.dto.Requests.AddStock;
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
    // READ (single)
    // -----------------------------
    @GetMapping("/{id}")
    public Stock getById(@PathVariable long id) {
        return service.getById(id);
    }

    // -----------------------------
    // UPDATE
    // -----------------------------
    @PutMapping("/{id}")
    public Stock update(@PathVariable long id, @RequestBody UpdateStock request) {
        return service.update(id, request);
    }

    // -----------------------------
    // DELETE
    // -----------------------------
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
