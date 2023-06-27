package com.example.b2capi.domain.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Long id;
    private String name;
    private String desc;
    private Long price;
    private String type;
    private Map<String, Integer> stocks;
}
