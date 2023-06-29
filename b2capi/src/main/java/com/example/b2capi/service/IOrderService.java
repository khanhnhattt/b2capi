package com.example.b2capi.service;

import com.example.b2capi.domain.dto.ChangeOrderSuccessDTO;
import com.example.b2capi.domain.dto.ChangeShippingSuccessDTO;
import com.example.b2capi.domain.dto.ViewOrdersDTO;
import com.example.b2capi.domain.enums.ShippingStatus;

import java.util.List;

public interface IOrderService {

    List<ViewOrdersDTO> viewOrdersByUser();

    ViewOrdersDTO viewOrderDetailById(Long orderId);

    void cancelOrderById(Long orderId);

    ChangeOrderSuccessDTO changeOrderStatusById(Long orderId);

    ChangeShippingSuccessDTO changeShippingStatusById(Long orderId, ShippingStatus shippingStatus);
}
