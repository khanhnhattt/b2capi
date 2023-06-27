package com.example.b2capi.domain.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDTO {
    private String name;
    private String desc;
    private Long price;
    private Long typeId;
    private List<StockDTO> stockDTOS;
}
