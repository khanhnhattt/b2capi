package com.example.b2capi.repository;

import com.example.b2capi.domain.dto.SearchProductDTO;
import com.example.b2capi.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductRepositoryCustom {
    List<Product> searchAll(SearchProductDTO searchProductDTO);
}
