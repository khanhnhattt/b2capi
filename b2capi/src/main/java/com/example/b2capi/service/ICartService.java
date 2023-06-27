package com.example.b2capi.service;

import com.example.b2capi.domain.dto.AddToCartDTO;
import org.springframework.stereotype.Service;

@Service
public interface ICartService {
    String addToCart(Long productId, AddToCartDTO addToCartDTO);

}
