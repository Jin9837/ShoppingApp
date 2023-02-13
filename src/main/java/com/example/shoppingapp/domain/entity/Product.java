package com.example.shoppingapp.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", unique = true, nullable = false)
    private int productId;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "retailPrice", nullable = false)
    private float retailPrice;
    @Column(name = "wholesalePrice", nullable = false)
    private float wholesalePrice;
    @Column(name = "stockQuantity", nullable = false)
    private int stockQuantity;

    public Product(int productId, String name, String description, float retailPrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.retailPrice = retailPrice;
    }
}
