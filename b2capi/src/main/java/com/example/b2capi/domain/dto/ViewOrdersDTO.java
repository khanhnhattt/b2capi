package com.example.b2capi.domain.dto;

import com.example.b2capi.domain.enums.OrderStatus;
import com.example.b2capi.domain.enums.ShippingStatus;
import com.example.b2capi.domain.model.Cart;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewOrdersDTO {
    private Long id;
    private LocalDateTime orderTime;
    private String address;
    private String tel;
    private List<ViewCartDetailsDTO> carts;
    private ShippingStatus shippingStatus;
    private OrderStatus orderStatus;

}
