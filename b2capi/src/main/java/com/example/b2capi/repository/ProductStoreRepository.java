package com.example.b2capi.repository;

import com.example.b2capi.domain.model.Product;
import com.example.b2capi.domain.model.ProductStore;
import com.example.b2capi.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
    ProductStore findByProductAndStore(Product p, Store s);

}
