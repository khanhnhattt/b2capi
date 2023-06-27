package com.example.b2capi.controller;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.service.ICartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CartController extends BaseController {

    @Autowired
    ICartService cartService;

    @PostMapping("/{id}/add-to-cart")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestBody @Valid AddToCartDTO addToCartDTO) {
        return createSuccessResponse("Add to Cart successfully", cartService.addToCart(id, addToCartDTO));
    }
}
