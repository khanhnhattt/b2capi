package com.example.b2capi.service;

import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.domain.dto.NewProductDTO;
import com.example.b2capi.domain.dto.ProductDetailDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public interface IProductService {
    MessageResponse addProduct(NewProductDTO newProductDTO);

    ProductDetailDTO viewDetail(Long id);

}
