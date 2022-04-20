package com.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.domain.models.entities.Product;
import com.domain.models.repos.ProductCacheRepo;
import com.domain.models.repos.ProductRepo;
import com.domain.redis.entity.ProductCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductCacheRepo productCacheRepo;

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product findOne(Long id) {
        ProductCache productCache;
        productCache = productCacheRepo.findProductById(id);
        if (productCache != null) {
            return new Product(productCache.getId(), productCache.getName(), productCache.getDescription(),
                    productCache.getPrice());
        }

        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            return null;
        }

        productCache = new ProductCache(product.get().getId(), product.get().getName(),
                product.get().getDescription(), product.get().getPrice());
        productCacheRepo.save(productCache);
        return product.get();
    }

    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    public void removeOne(Long id) {
        productRepo.deleteById(id);
        productCacheRepo.deleteProduct(id);
    }

    public List<Product> findByName(String name) {
        return productRepo.findByNameContains(name);
    }
}
