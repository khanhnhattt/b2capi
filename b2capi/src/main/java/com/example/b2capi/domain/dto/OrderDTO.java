package com.example.b2capi.domain.dto;

import com.example.b2capi.domain.enums.PaymentMethod;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
//    private ViewCheckoutDTO viewCheckoutDTO;
    private PaymentMethod paymentMethod;
}
