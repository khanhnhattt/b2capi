package com.example.b2capi.repository;

import com.example.b2capi.domain.model.Cart;
import com.example.b2capi.domain.model.Order;
import com.example.b2capi.domain.model.Store;
import com.example.b2capi.domain.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);

    @Query(value = "SELECT * FROM b2c.cart where user_id = :userId and product_id = :productId and order_id is null;", nativeQuery = true)
    Optional<Cart> findByUserAndProduct(Long userId, Long productId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE `b2c`.`cart` SET `order_id` = :orderId WHERE (user_id = :userId);", nativeQuery = true)
    void updateOrderIdByUser(Long orderId, Long userId);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE `b2c`.`cart` SET `store_id` = :store WHERE (`id` = :cart);", nativeQuery = true)
//    void updateStoreById(Store store, Cart cart);
}
