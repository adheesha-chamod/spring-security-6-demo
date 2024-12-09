package com.demo.springSecurityDemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private record Product(Integer id, String name, double price) {}

    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Laptop", 1000.0),
            new Product(2, "Mobile", 500.0),
            new Product(3, "Tablet", 300.0)
    ));

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }

    @GetMapping
    public List<Product> getProducts() {
        return products;
    }
}