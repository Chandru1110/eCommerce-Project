package com.springh2.controller;

import com.springh2.model.Product;
import com.springh2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> product()
    {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> productById(@PathVariable int id)
    {
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @PostMapping("/post")
    public void updateNewProduct(@RequestBody Product p)
    {
        productService.updateNew(p);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody Product p)
    {
        productService.update(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id)
    {
        productService.delete(id);
    }

    @GetMapping("/search")  // use ?Keyword=c in postman
    public ResponseEntity<List<Product>> searchProducts (@RequestParam String Keyword)
    {

        List<Product> p = productService.searchProducts(Keyword);
        System.out.println("searching with " + Keyword);
        return new ResponseEntity<>(p,HttpStatus.OK);

    }

}
