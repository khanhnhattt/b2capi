package com.example.b2capi.controller;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.domain.dto.NewProductDTO;
import com.example.b2capi.domain.dto.SearchProductDTO;
import com.example.b2capi.domain.dto.ViewAllProductsDTO;
import com.example.b2capi.service.ICartService;
import com.example.b2capi.service.IProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController extends BaseController {

    @Autowired
    IProductService productService;

    @Autowired
    ICartService cartService;

    @PostMapping("/admin/add")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody @Valid NewProductDTO newProductDTO) {
        return createSuccessResponse("New Product added successfully!", productService.addProduct(newProductDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewProductDetail(@PathVariable Long id) {
        return createSuccessResponse("Product Detail id: " + id, productService.viewDetail(id));
    }

    @PostMapping("/{id}/add-to-cart")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestBody @Valid AddToCartDTO addToCartDTO) {
        return createSuccessResponse("Add to Cart successfully", cartService.addToCart(id, addToCartDTO));
    }

    @GetMapping("/")
    public Page<ViewAllProductsDTO> viewAllProducts(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return productService.getProductPagination(pageNumber - 1, pageSize, null);
    }

    @GetMapping("/search")
    public Page<ViewAllProductsDTO> searchProducts(
            SearchProductDTO searchProductDTO,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {
        return productService.getProductSearchPagination(searchProductDTO, pageNumber, pageSize);
    }
}
