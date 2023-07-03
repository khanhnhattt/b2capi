package com.example.b2capi.domain.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeDTO {
    private Long productId;
    private String productName;
}
