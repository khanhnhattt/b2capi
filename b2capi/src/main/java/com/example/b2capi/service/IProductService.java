package com.example.b2capi.service;

import com.example.b2capi.domain.dto.NewProductDTO;
import com.example.b2capi.domain.dto.ProductDetailDTO;
import com.example.b2capi.domain.dto.SearchProductDTO;
import com.example.b2capi.domain.dto.ViewAllProductsDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

public interface IProductService {
    MessageResponse addProduct(NewProductDTO newProductDTO);

    ProductDetailDTO viewDetail(Long id);


    Page<ViewAllProductsDTO> getProductPagination(Integer pageNumber, Integer pageSize, Sort sort);

    Page<ViewAllProductsDTO> getProductSearchPagination(SearchProductDTO searchProductDTO, Integer pageNumber, Integer pageSize);
}
