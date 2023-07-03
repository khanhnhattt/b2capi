package com.example.b2capi.repository.impl;

import com.example.b2capi.domain.dto.SearchProductDTO;
import com.example.b2capi.domain.model.Product;
import com.example.b2capi.repository.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Product> searchAll(SearchProductDTO searchProductDTO) {

        String search = searchProductDTO.getSearch();
        Long productTypeId = searchProductDTO.getProductTypeId();
        Long minPrice = searchProductDTO.getMinPrice() != null ? searchProductDTO.getMinPrice() : 0;
        Long maxPrice = searchProductDTO.getMaxPrice() != null ? searchProductDTO.getMaxPrice() : Long.MAX_VALUE;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" WHERE 1=1 ");

        if (productTypeId != null) {
            sqlQuery.append(" AND `product_type_id` = " + productTypeId);
        }
        sqlQuery.append(" AND `name` like '%" + search + "%' ");
        sqlQuery.append(" AND `price` between " + minPrice + " and " + maxPrice + ";");

        String selectQuery = "SELECT * FROM b2c.product p " + sqlQuery;

        Query query = entityManager.createNativeQuery(selectQuery, Product.class);

        return query.getResultList();
    }
}
