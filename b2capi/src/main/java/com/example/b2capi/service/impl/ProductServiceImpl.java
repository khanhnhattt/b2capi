package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.domain.dto.NewProductDTO;
import com.example.b2capi.domain.dto.ProductDetailDTO;
import com.example.b2capi.domain.dto.StockDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.Product;
import com.example.b2capi.domain.model.ProductStore;
import com.example.b2capi.domain.model.ProductType;
import com.example.b2capi.domain.model.Store;
import com.example.b2capi.repository.ProductRepository;
import com.example.b2capi.repository.ProductStoreRepository;
import com.example.b2capi.repository.ProductTypeRepository;
import com.example.b2capi.repository.StoreRepository;
import com.example.b2capi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final StoreRepository storeRepository;
    private final ProductStoreRepository productStoreRepository;

    @Override
    public MessageResponse addProduct(NewProductDTO newProductDTO) {
        // Check product type
        ProductType type = productTypeRepository.findById(newProductDTO.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Product Type not exist"));

        Product product = new Product(newProductDTO.getName(), newProductDTO.getPrice(), newProductDTO.getDesc(), type);

        // Add new product
        productRepository.save(product);

        List<ProductStore> productStores = mapToProductStore(newProductDTO.getStockDTOS(), product);
        for (var ps : productStores)
            productStoreRepository.save(ps);

        return MessageResponse.builder().name("Added Successfully").build();
    }

    private List<ProductStore> mapToProductStore(List<StockDTO> stockDTOS, Product product) {
        List<ProductStore> list = new ArrayList<>();
        for (var store : stockDTOS) {
            // Check store
            Store s = storeRepository.findById(store.getStoreId())
                    .orElseThrow(() -> new IllegalArgumentException("Store not exist"));

            ProductStore productStore = new ProductStore(product, s, store.getQuantity());
            list.add(productStore);
        }
        return list;
    }

    @Override
    public ProductDetailDTO viewDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Get Store - Quantity
        Map<String, Integer> stocks = new HashMap<>();
        for (var s : product.getProductStores())
            stocks.put(s.getStore().getName(), s.getQuantity());

        ProductDetailDTO productDetailDTO = new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getDesc(),
                product.getPrice(),
                product.getProductType().getName(),
                stocks
        );

        return productDetailDTO;
    }
}
