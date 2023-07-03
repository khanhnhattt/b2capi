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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl extends BaseService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ShipperRepository shipperRepository;
    private final OrderRepository orderRepository;
    private final ProductStoreRepository psRepository;
    private final StoreRepository storeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addToCart(Long productId, AddToCartDTO addToCartDTO) {
        User user = getUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        log.info("Product Id found: "+product.getId());

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

        List<Cart> carts = cartRepository.findAllByUserAndOrderIsNull(user);

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

        // Get Storage for order
        List<Cart> cartList = this.getStorage();

        // Find shipper w/ the least orders
        Optional<Shipper> shipper = shipperRepository.findByLeastOrder();

        // Create new order
        Order order = new Order(
                LocalDateTime.now(),
                user.getAddress(),
                user.getTel(),
                user,
                cartList,
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

    private List<Cart> getStorage() {
        List<Cart> cartList = cartRepository.findAllByUserAndOrderIsNull(getUser());
        int size = cartList.size();
        List<Store> stores = storeRepository.findAll();

        while (size > 0) {
            cartList = cartRepository.findAllByUserAndOrderIsNull(getUser());
            // Get list of available items in each store

            Map<Store, List<Cart>> storeItemsAvailable = getStoreItemsAvailable(cartList, stores);

            // Update store w/ the highest items available and update remaining item
            size -= updateStoreWithLongestItems(storeItemsAvailable);

        }

        return cartRepository.findAllByUserAndOrderIsNull(getUser());
    }

    private int updateStoreWithLongestItems(Map<Store, List<Cart>> storeItemsAvailable) {
        Store chosenStore = new Store();
        List<Cart> carts = new ArrayList<>();
        int longestSize = 0;

        // Find store w/ the longest items list
        for (Map.Entry<Store, List<Cart>> entry : storeItemsAvailable.entrySet()) {
            Store key = entry.getKey();
            List<Cart> value = entry.getValue();
            int size = value.size();

            if (size > longestSize) {
                chosenStore = key;
                carts = value;
                longestSize = size;
            }
        }

        // Update store in table Cart
        for (var c : carts) {
//            cartRepository.updateStoreByCart(chosenStore, c);
            Optional<Cart> cart = cartRepository.findById(c.getId());
            cart.get().setStore(chosenStore);
            cartRepository.save(cart.get());
        }

        return longestSize;

    }

    private Map<Store, List<Cart>> getStoreItemsAvailable(List<Cart> carts, List<Store> stores) {
        Map<Store, List<Cart>> storeItemsAvailable = new HashMap<>();
        for (var s : stores) {
            List<Cart> cartList = new ArrayList<>();
            for (var c : carts) {
                if (c.getStore() == null) {
                    Optional<Product> p = productRepository.findByName(c.getProduct().getName());
                    ProductStore ps = psRepository.findByProductAndStore(p.get(), s);
                    if (ps != null && c.getQuantity() <= ps.getQuantity()) {
                        cartList.add(c);
                    }
                }
            }
            storeItemsAvailable.put(s, cartList);
        }
        return storeItemsAvailable;
    }
}
