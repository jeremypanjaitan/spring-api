package com.domain.models.repos;

import com.domain.redis.entity.ProductCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCacheRepo {

    public static final String HASH_KEY = "Product";

    @Autowired
    private RedisTemplate<String, Object> template;

    public ProductCache save(ProductCache product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public ProductCache findProductById(Long id) {
        return (ProductCache) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProduct(Long id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "product removed !!";
    }

}
