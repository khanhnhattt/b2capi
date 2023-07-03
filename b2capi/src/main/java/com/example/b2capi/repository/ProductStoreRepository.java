package com.example.b2capi.repository;

import com.example.b2capi.domain.dto.AvailableItemDTO;
import com.example.b2capi.domain.model.Product;
import com.example.b2capi.domain.model.ProductStore;
import com.example.b2capi.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
    ProductStore findByProductAndStore(Product p, Store s);

    @Query(value = "SELECT product_id as id, SUM(quantity) as total FROM b2c.product_store group by product_id;", nativeQuery = true)
    List<AvailableItemDTO> countAllQuantity();
}
