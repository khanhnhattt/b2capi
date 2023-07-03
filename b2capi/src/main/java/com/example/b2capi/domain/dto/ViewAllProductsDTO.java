package com.example.b2capi.domain.dto;

import com.example.b2capi.domain.model.ProductType;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewAllProductsDTO {
    private Long id;
    private String name;
    private Long price;
    private boolean available;
    private ProductTypeDTO productType;
}
