package com.example.b2capi.controller;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.domain.dto.NewProductDTO;
import com.example.b2capi.service.ICartService;
import com.example.b2capi.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    IProductService productService;

    @Autowired
    ICartService cartService;

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid NewProductDTO newProductDTO)
    {
        return createSuccessResponse("New Product added successfully!", productService.addProduct(newProductDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewProductDetail(@PathVariable Long id)
    {
        return createSuccessResponse("Product Detail id: "+id, productService.viewDetail(id));
    }

    @PostMapping("/{id}/add-to-cart")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestBody @Valid AddToCartDTO addToCartDTO) {
        return createSuccessResponse("Add to Cart successfully", cartService.addToCart(id, addToCartDTO));
    }
}
