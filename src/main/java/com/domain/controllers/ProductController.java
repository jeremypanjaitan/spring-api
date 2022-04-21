package com.domain.controllers;

import com.domain.dto.JsonResponse;
import com.domain.models.entities.Product;
import com.domain.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JsonResponse<Product>> createOne(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        JsonResponse<Product> response = new JsonResponse<Product>(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(), savedProduct);
        return new ResponseEntity<JsonResponse<Product>>(response, null, HttpStatus.OK.value());
    }

    @GetMapping
    public ResponseEntity<JsonResponse<Iterable<Product>>> findAll() {
        Iterable<Product> products = productService.findAll();
        JsonResponse<Iterable<Product>> response = new JsonResponse<Iterable<Product>>(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(), products);
        return new ResponseEntity<JsonResponse<Iterable<Product>>>(response, null, HttpStatus.OK.value());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse<Product>> findOne(@PathVariable("id") Long id) {
        Product product = productService.findOne(id);
        if (product == null) {

            JsonResponse<Product> response = new JsonResponse<Product>(HttpStatus.BAD_REQUEST.value(), "data not found",
                    null);
            return new ResponseEntity<JsonResponse<Product>>(response, null, HttpStatus.BAD_REQUEST.value());
        } else {
            JsonResponse<Product> response = new JsonResponse<Product>(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), product);
            return new ResponseEntity<JsonResponse<Product>>(response, null, HttpStatus.OK.value());
        }
    }

    @PutMapping
    public ResponseEntity<JsonResponse<Product>> update(@RequestBody Product product) {
        Product productAvailable = productService.findOne(product.getId());
        if (productAvailable == null) {
            JsonResponse<Product> response = new JsonResponse<Product>(HttpStatus.BAD_REQUEST.value(), "data not found",
                    null);
            return new ResponseEntity<JsonResponse<Product>>(response, null, HttpStatus.BAD_REQUEST);
        }
        Product updatedProduct = productService.update(product);
        JsonResponse<Product> response = new JsonResponse<Product>(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(), updatedProduct);
        return new ResponseEntity<JsonResponse<Product>>(response, null, HttpStatus.OK.value());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse<Object>> removeOne(@PathVariable("id") Long id) {
        try {
            productService.removeOne(id);
            JsonResponse<Object> response = new JsonResponse<Object>(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), null);
            return new ResponseEntity<JsonResponse<Object>>(response, null, HttpStatus.OK.value());
        } catch (EmptyResultDataAccessException err) {
            JsonResponse<Object> response = new JsonResponse<Object>(HttpStatus.BAD_REQUEST.value(), "data not found",
                    null);
            return new ResponseEntity<JsonResponse<Object>>(response, null, HttpStatus.BAD_REQUEST.value());
        }
    }
}
