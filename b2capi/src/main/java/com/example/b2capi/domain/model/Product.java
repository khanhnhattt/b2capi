package com.example.b2capi.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "price" ,nullable = false)
    private Long price;

    @Column(name = "description", nullable = false)
    private String desc;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @ManyToMany(mappedBy = "products")
    private List<Store> stores;

    @OneToMany(mappedBy = "product")        // 1-n to product_order
    private List<ProductOrder> productOrders;

//    @OneToMany(mappedBy = "product")    // 1-n to feedback
//    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product")    // 1-n to product_image
    private List<ProductImage> images;

}
