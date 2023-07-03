package com.example.b2capi.domain.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductDTO {
    private String search;
    private Long productTypeId;
    private boolean available;
    private Long minPrice, maxPrice;
}
