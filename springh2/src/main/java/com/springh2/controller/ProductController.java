package com.springh2.controller;

import com.springh2.exception.ProductNotFoundException;
import com.springh2.model.Product;
import com.springh2.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String welcome (HttpServletRequest request)
    {
        return "Welcome " + request.getSession().getId();
    }

    @GetMapping("/csrf")
    public CsrfToken getCrsfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> product()
    {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> productById(@PathVariable int id) throws ProductNotFoundException
    {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/post")
    public ResponseEntity<Product> updateNewProduct(@RequestBody @Valid  Product p) {
        productService.updateNew(p);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product p) throws ProductNotFoundException {
        productService.update(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")  // use ?Keyword=c in postman
    public ResponseEntity<List<Product>> searchProducts (@RequestParam String Keyword)
    {

        List<Product> p = productService.searchProducts(Keyword);
        System.out.println("searching with " + Keyword);
        return new ResponseEntity<>(p,HttpStatus.OK);

    }

}
