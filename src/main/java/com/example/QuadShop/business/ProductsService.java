package com.example.QuadShop.business;

import com.example.QuadShop.business.Mapper.ToDomain;
import com.example.QuadShop.domain.Product;
import com.example.QuadShop.persistence.ProductsRepo;
import com.example.QuadShop.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductsService {
    public final ProductsRepo productsRepo;


    public List<Product> GetProductsByCategory(String category){
         List<ProductEntity> entities = productsRepo.findAllByCategory(category);
         List<Product> products = new ArrayList<>();
         for (ProductEntity entity : entities) {
             products.add(ToDomain.toProduct(entity));
         }

         return products;
    }
    public List<Product> GetAllProdducts(){
        List<ProductEntity> entities = productsRepo.findAll();
        List<Product> products = new ArrayList<>();
        for (ProductEntity entity : entities) {
            products.add(ToDomain.toProduct(entity));
        }
        return products;
    }
    public Product GetProductById(Long id){
        ProductEntity entity = productsRepo.findById(id).orElse(null);
        return ToDomain.toProduct(entity);
    }
}
