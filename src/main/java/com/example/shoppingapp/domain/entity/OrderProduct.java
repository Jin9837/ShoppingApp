package com.example.shoppingapp.domain.entity;

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
    @Column(name = "orderId", nullable = false)
    private int orderId;
    @Column(name = "productId", nullable = false)
    private int productId;

    @Column(name = "purchasedQuantity", nullable = false)
    private int purchasedQuantity;

    @Column(name = "executionRetailPrice", nullable = false)
    private float executionRetailPrice;
    @Column(name = "executionWholesalePrice", nullable = false)
    private float executionWholesalePrice;
}
