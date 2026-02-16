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
             products.add(ToDomain.Product(entity));
         }
         return products;
    }
    public List<Product> GetAllProducts(){
        List<ProductEntity> entities = productsRepo.findAll();
        System.out.println(entities.getFirst().getImages());
        List<Product> products = new ArrayList<>();
        for (ProductEntity entity : entities) {
            products.add(ToDomain.Product(entity));
        }
        return products;
    }

    public List<Product> SearchProducts(String name){
        List<ProductEntity> entities = productsRepo.findAllByNameContains(name);
        List<Product> products = new ArrayList<>();
        for (ProductEntity entity : entities) {
            products.add(ToDomain.Product(entity));
        }
        return products;
    }

    public Product GetProductById(Long id){
        ProductEntity entity = productsRepo.findById(id).orElse(null);
        System.out.println(entity);
        return ToDomain.Product(entity);
    }
}
