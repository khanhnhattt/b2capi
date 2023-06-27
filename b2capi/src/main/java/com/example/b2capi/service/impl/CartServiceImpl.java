package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.domain.model.Cart;
import com.example.b2capi.domain.model.Product;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.*;
import com.example.b2capi.service.BaseService;
import com.example.b2capi.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl extends BaseService implements ICartService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public String addToCart(Long productId, AddToCartDTO addToCartDTO) {
        User user = getUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Calculate newly added price
        Long price = product.getPrice() * addToCartDTO.getQuantity();

        // Find Cart by User
        Optional<Cart> cart = cartRepository.findByUserAndProduct(user.getId(), productId);

        /*
         * Add to ProductCart
         * if existed, update quantity
         * else add new
         */
        if (!cart.isPresent())      //not existed then add new
        {
            Cart c = new Cart(addToCartDTO.getQuantity(), price, product, user);
            cartRepository.save(c);
        } else {
            price += cart.get().getPrice();
            cart.get().setPrice(price);

            Integer oldQuantity = cart.get().getQuantity();
            cart.get().setQuantity(oldQuantity + addToCartDTO.getQuantity());

            cartRepository.save(cart.get());
        }

        return "Added to Cart";
    }
}
