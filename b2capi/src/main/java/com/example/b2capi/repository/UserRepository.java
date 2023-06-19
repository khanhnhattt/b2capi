package com.example.b2capi.repository;

import com.example.b2capi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail (String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM b2c.user where email = :emailOrUsername or username = :emailOrUsername", nativeQuery = true)
    Optional<User> findByEmailOrUsername(String emailOrUsername);
}
