package com.example.b2capi.domain.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDTO {
    private Integer quantity;
}
