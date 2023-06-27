package com.example.b2capi.repository;

import com.example.b2capi.domain.model.Order;
import com.example.b2capi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUser(User user);
}
