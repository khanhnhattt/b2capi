package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.AddToCartDTO;
import com.example.b2capi.domain.dto.ViewCartDetailsDTO;
import com.example.b2capi.domain.dto.ViewCheckoutDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.enums.OrderStatus;
import com.example.b2capi.domain.enums.PaymentMethod;
import com.example.b2capi.domain.enums.ShippingStatus;
import com.example.b2capi.domain.model.*;
import com.example.b2capi.repository.*;
import com.example.b2capi.service.BaseService;
import com.example.b2capi.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl extends BaseService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ShipperRepository shipperRepository;
    private final OrderRepository orderRepository;
    private final ProductStoreRepository psRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addToCart(Long productId, AddToCartDTO addToCartDTO) {
        User user = getUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Calculate newly added price
        Long price = product.getPrice() * addToCartDTO.getQuantity();

        // Find Cart by User
        Optional<Cart> cart = cartRepository.findByUserAndProduct(user.getId(), productId);

        /*
         * Add to ProductCart
         * if existed, update quantity
         * else add new
         */
        if (!cart.isPresent())      //not existed then add new
        {
            Cart c = new Cart(addToCartDTO.getQuantity(), price, product, user);
            cartRepository.save(c);
        } else {
            price += cart.get().getPrice();
            cart.get().setPrice(price);

            Integer oldQuantity = cart.get().getQuantity();
            cart.get().setQuantity(oldQuantity + addToCartDTO.getQuantity());

            cartRepository.save(cart.get());
        }

        return "Added to Cart";
    }

    @Override
    public List<ViewCartDetailsDTO> viewCart() {
        User user = getUser();

        List<Cart> carts = cartRepository.findAllByUser(user);

        List<ViewCartDetailsDTO> viewCartDetailsDTOS = carts
                .stream()
                .map(cart -> modelMapper.map(cart, ViewCartDetailsDTO.class))
//                .map(cart -> mapToViewCartDetailsDTO(cart))
                .toList();

        return viewCartDetailsDTOS;
    }

    @Override
    public ViewCheckoutDTO viewCheckout() {
        User user = getUser();

        List<ViewCartDetailsDTO> viewCartDetailsDTO = viewCart();

        Long totalPrice = 0l;
        for (var d : viewCartDetailsDTO) {
            totalPrice += d.getPrice();
        }

        ViewCheckoutDTO viewCheckoutDTO = new ViewCheckoutDTO(viewCartDetailsDTO, totalPrice, user.getAddress(), user.getTel());

        return viewCheckoutDTO;
    }

    @Override
    public MessageResponse placeOrder(PaymentMethod paymentMethod) {
        User user = getUser();

        List<ViewCartDetailsDTO> viewCartDetailsDTO = viewCart();
        // Map ViewCartDetailDTO to Cart
        List<Cart> carts = viewCartDetailsDTO
                .stream()
                .map(vcdDto -> modelMapper.map(vcdDto, Cart.class))
                .toList();

        // Get Storage for order
        this.getStorage(carts);

        // Find shipper w/ the least orders
        Optional<Shipper> shipper = shipperRepository.findByLeastOrder();

        // Create new order
        Order order = new Order(
                LocalDateTime.now(),
                user.getAddress(),
                user.getTel(),
                user,
                carts,
                shipper.get(),
                ShippingStatus.PROCESSING,
                OrderStatus.UNCONFIRMED
        );
        orderRepository.save(order);

        // Update orderId in chart
        Long orderId = order.getId();
        cartRepository.updateOrderIdByUser(orderId, getUser().getId());

        return MessageResponse.builder().name("Order success").build();
    }

    private void getStorage(List<Cart> carts) {

    }
}
