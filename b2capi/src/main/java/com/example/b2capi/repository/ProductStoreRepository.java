package com.example.b2capi.repository;

import com.example.b2capi.domain.model.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
}
