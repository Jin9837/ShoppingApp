package com.example.shoppingapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="OrderProduct")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderProductId", unique = true, nullable = false)
    private int orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // ignore the product field during serialization
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "purchasedQuantity", nullable = false)
    private int purchasedQuantity;

    @Column(name = "executionRetailPrice", nullable = false)
    private float executionRetailPrice;

    @Column(name = "executionWholesalePrice", nullable = false)
    private float executionWholesalePrice;
}

