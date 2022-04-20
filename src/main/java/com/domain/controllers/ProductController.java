package com.domain.controllers;

import com.domain.dto.JsonResponse;
import com.domain.models.entities.Product;
import com.domain.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public JsonResponse<Product> createOne(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return new JsonResponse<Product>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), savedProduct);
    }

    @GetMapping
    public JsonResponse<Iterable<Product>> findAll() {
        Iterable<Product> products = productService.findAll();
        return new JsonResponse<Iterable<Product>>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), products);
    }

    @GetMapping("/{id}")
    public JsonResponse<Product> findOne(@PathVariable("id") Long id) {
        Product product = productService.findOne(id);
        if (product == null) {
            return new JsonResponse<Product>(HttpStatus.BAD_REQUEST.value(), "data not found",
                    null);
        } else {
            return new JsonResponse<Product>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), product);
        }
    }

    @PutMapping
    public JsonResponse<Product> update(@RequestBody Product product) {
        Product productAvailable = productService.findOne(product.getId());
        if (productAvailable == null) {
            return new JsonResponse<Product>(HttpStatus.BAD_REQUEST.value(), "data not found",
                    null);
        }
        Product updatedProduct = productService.update(product);
        return new JsonResponse<Product>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), updatedProduct);
    }

    @DeleteMapping("/{id}")
    public JsonResponse<Object> removeOne(@PathVariable("id") Long id) {
        try {
            productService.removeOne(id);
            return new JsonResponse<Object>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
        } catch (EmptyResultDataAccessException err) {
            return new JsonResponse<Object>(HttpStatus.BAD_REQUEST.value(), "data not found",
                    null);
        }
    }
}
