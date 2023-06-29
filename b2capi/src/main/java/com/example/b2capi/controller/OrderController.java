package com.example.b2capi.controller;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.enums.OrderStatus;
import com.example.b2capi.domain.enums.ShippingStatus;
import com.example.b2capi.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    IOrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> viewAllOrders() {
        return createSuccessResponse("List Orders by Id", orderService.viewOrdersByUser());
    }

    @GetMapping("/detail")
    public ResponseEntity<?> viewOrderDetail(@RequestParam Long orderId) {
        return createSuccessResponse("View Order Detail", orderService.viewOrderDetailById(orderId));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrderById(orderId);
        return createSuccessResponse("Order cancelled successfully", HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirm-order")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId) {
        return createSuccessResponse("Order confirmed successfully", orderService.changeOrderStatusById(orderId));
    }

    @PutMapping("/{orderId}/change-shipping-status")
//    @PreAuthorize("hasAuthority('SHIPPER')")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId, @RequestParam ShippingStatus shippingStatus) {
        return createSuccessResponse("Order Status changed successfully", orderService.changeShippingStatusById(orderId, shippingStatus));
    }
}
