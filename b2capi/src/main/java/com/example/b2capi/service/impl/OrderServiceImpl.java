package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.ChangeOrderSuccessDTO;
import com.example.b2capi.domain.dto.ChangeShippingSuccessDTO;
import com.example.b2capi.domain.dto.ViewOrdersDTO;
import com.example.b2capi.domain.enums.OrderStatus;
import com.example.b2capi.domain.enums.ShippingStatus;
import com.example.b2capi.domain.model.Order;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.OrderRepository;
import com.example.b2capi.service.BaseService;
import com.example.b2capi.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseService implements IOrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ViewOrdersDTO> viewOrdersByUser() {
        User user = getUser();

        List<Order> orders = orderRepository.findAllByUser(user);

        if (orders.isEmpty()) throw new IllegalArgumentException("No order yet");

        List<ViewOrdersDTO> viewOrdersDTOS = orders
                .stream()
                .map(order -> modelMapper.map(order, ViewOrdersDTO.class))
                .toList();

        return viewOrdersDTOS;
    }

    @Override
    public ViewOrdersDTO viewOrderDetailById(Long orderId) {
        User user = getUser();

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isEmpty()) throw new IllegalArgumentException("Order not found");

        if (order.get().getUser() == user) {
            ViewOrdersDTO viewOrdersDTO = modelMapper.map(order.get(), ViewOrdersDTO.class);
            return viewOrdersDTO;
        } else
            throw new IllegalArgumentException("Order ID invalid");
    }

    @Override
    public void cancelOrderById(Long orderId) {
        User user = getUser();

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent() && user == order.get().getUser()) {
            order.get().setOrderStatus(OrderStatus.CANCELLED);
            order.get().setShippingStatus(ShippingStatus.CANCELLED);
            orderRepository.save(order.get());
        } else throw new IllegalArgumentException("Order ID invalid");
    }

    @Override
    public ChangeOrderSuccessDTO changeOrderStatusById(Long orderId) {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            order.get().setOrderStatus(OrderStatus.IN_PROCESS);
            orderRepository.save(order.get());
        } else throw new IllegalArgumentException("Order ID Invalid");
        return new ChangeOrderSuccessDTO(orderId, OrderStatus.IN_PROCESS);
    }

    @Override
    public ChangeShippingSuccessDTO changeShippingStatusById(Long orderId, ShippingStatus shippingStatus) {

        if (ShippingStatus.CANCELLED.equals(shippingStatus) || ShippingStatus.PROCESSING.equals(shippingStatus))
            throw new IllegalArgumentException("Shipping status input invalid");

        Optional<Order> order = orderRepository.findById(orderId);

        OrderStatus orderStatus = shippingStatus.equals(ShippingStatus.DELIVERING) ? OrderStatus.DELIVERY : OrderStatus.DONE;

        if (order.isPresent()) {
            order.get().setOrderStatus(orderStatus);
            order.get().setShippingStatus(shippingStatus);
            orderRepository.save(order.get());
        }
        return new ChangeShippingSuccessDTO(orderId, orderStatus, shippingStatus);
    }

}
