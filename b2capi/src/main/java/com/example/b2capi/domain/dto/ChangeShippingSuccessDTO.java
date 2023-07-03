package com.example.b2capi.domain.dto;

import com.example.b2capi.domain.enums.OrderStatus;
import com.example.b2capi.domain.enums.ShippingStatus;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeShippingSuccessDTO {
    private Long orderId;
    private OrderStatus orderStatus;
    private ShippingStatus shippingStatus;
}
