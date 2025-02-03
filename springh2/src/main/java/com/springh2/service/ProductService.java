package com.springh2.service;


import com.springh2.exception.ProductNotFoundException;
import com.springh2.model.Product;
import com.springh2.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

    public List<Product> getProducts(){
        return repo.findAll();
    }

    public Product getProductById(int id) throws ProductNotFoundException {

        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for id : " + id));


    }

    @Transactional
    public void updateNew(Product p) {
        repo.save(p);

    }

    @Transactional
    public void update(Product product) throws ProductNotFoundException {
        Product existingProduct = repo.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found for id : " + product.getId()));
        existingProduct.setProduct(product.getProduct());
        existingProduct.setRate(product.getRate());
        // Don't manually set version, Hibernate handles it
        repo.save(existingProduct);
    }

//    public void update(Product p) {
//        repo.save(p);
//    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
