package com.example.b2capi.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`feedback`")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating", nullable = false)
    private Long rating;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_order_id", referencedColumnName = "id")
    private ProductOrder productOrder;

}
