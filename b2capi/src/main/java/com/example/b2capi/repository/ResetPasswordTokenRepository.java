package com.example.b2capi.repository;

import com.example.b2capi.domain.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
     ResetPasswordToken findByToken(String token);


}
