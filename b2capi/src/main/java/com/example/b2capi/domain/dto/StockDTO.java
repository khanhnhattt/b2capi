package com.example.b2capi.domain.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private Long storeId;
    private Integer quantity;
}
