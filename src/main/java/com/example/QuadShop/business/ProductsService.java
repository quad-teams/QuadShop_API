package com.example.QuadShop.business;

import com.example.QuadShop.business.Mapper.ToDomain;
import com.example.QuadShop.controller.dto.Requests.AddProduct;
import com.example.QuadShop.controller.dto.Requests.UpdateProduct;
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

    public long CreateProduct(AddProduct request) {
        ProductEntity product = new ProductEntity();
        product.setName(request.name);
        product.setCategory(request.category);
        product.setDescription(request.description);
        product.setPrice(request.price);

        return productsRepo.save(product).getId();
    }

    public void EditProduct(UpdateProduct request) {
        ProductEntity product = productsRepo.findById(request.id).orElse(null);
        if (product == null) return;
        product.setName(request.name);
        product.setCategory(request.category);
        product.setDescription(request.description);
        product.setPrice(request.price);
        productsRepo.save(product);
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

    public Product GetProductById(Long id){
        ProductEntity entity = productsRepo.findById(id).orElse(null);
        return ToDomain.Product(entity);
    }

    public void delete(long id) {
        productsRepo.deleteById(id);
    }
}
