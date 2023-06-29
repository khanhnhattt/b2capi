package com.example.b2capi.domain.dto;

import com.example.b2capi.domain.enums.OrderStatus;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOrderSuccessDTO {
    private Long orderId;
    private OrderStatus orderStatus;
}
