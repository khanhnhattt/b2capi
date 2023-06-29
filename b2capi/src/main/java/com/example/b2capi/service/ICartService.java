package com.example.b2capi.service;

import com.example.b2capi.domain.dto.auth.AddToCartDTO;
import com.example.b2capi.domain.dto.ViewCartDetailsDTO;
import com.example.b2capi.domain.dto.ViewCheckoutDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.enums.PaymentMethod;

import java.util.List;

public interface ICartService {
    String addToCart(Long productId, AddToCartDTO addToCartDTO);

    List<ViewCartDetailsDTO> viewCart();

    ViewCheckoutDTO viewCheckout();

    MessageResponse placeOrder(PaymentMethod paymentMethod);
}
