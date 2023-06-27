package com.example.b2capi.domain.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCheckoutDTO {
    private List<ViewCartDetailsDTO> viewCartDetailsDTOS;
    private Long totalPrice;
    private String address;
    private String tel;
}
