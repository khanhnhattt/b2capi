package com.example.b2capi.controller;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.enums.PaymentMethod;
import com.example.b2capi.service.ICartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cart")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CartController extends BaseController {

    @Autowired
    private ICartService cartService;

    @GetMapping("")
    public ResponseEntity<?> viewCartDetail()
    {
        return createSuccessResponse("View Cart Details", cartService.viewCart());
    }

    @GetMapping("/checkout")
    public ResponseEntity<?> viewCheckout()
    {
        return createSuccessResponse("View Checkout", cartService.viewCheckout());
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody PaymentMethod paymentMethod)
    {
        return createSuccessResponse("Ordered successfully", cartService.placeOrder(paymentMethod));
    }

}
