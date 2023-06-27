package com.example.b2capi.domain.model;

import com.example.b2capi.domain.enums.OrderStatus;
import com.example.b2capi.domain.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "address")
    private String address;

    @Column(name = "tel")
    private String tel;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)       // 1-n to product_order
    private List<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

    @Column(name = "shipping_status")
    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(LocalDateTime orderTime, String address, String tel, User user, List<Cart> carts, Shipper shipper, ShippingStatus shippingStatus, OrderStatus orderStatus) {
        this.orderTime = orderTime;
        this.address = address;
        this.tel = tel;
        this.user = user;
        this.carts = carts;
        this.shipper = shipper;
        this.shippingStatus = shippingStatus;
        this.orderStatus = orderStatus;
    }
}
