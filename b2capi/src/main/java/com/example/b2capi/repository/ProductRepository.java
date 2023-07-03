package com.example.b2capi.repository;

import com.example.b2capi.domain.dto.SearchProductDTO;
import com.example.b2capi.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

}
