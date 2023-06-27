package com.example.b2capi.repository;

import com.example.b2capi.domain.model.Cart;
import com.example.b2capi.domain.model.Order;
import com.example.b2capi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);

    @Query(value = "SELECT * FROM b2c.cart where user_id = :userId and product_id = :productId and order_id is null;", nativeQuery = true)
    Optional<Cart> findByUserAndProduct(Long userId, Long productId);

}
