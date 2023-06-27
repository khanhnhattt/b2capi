package com.example.b2capi.domain.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCartDetailsDTO {
    private Long cartId;
    private Integer quantity;
    private Long price;
    private String productName;
}
