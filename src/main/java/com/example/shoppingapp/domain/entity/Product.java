package com.example.shoppingapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"productWatchLists"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", unique = true, nullable = false)
    private int productId;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;

    @Positive(message = "Retail price must be greater than 0.")
    @Column(name = "retailPrice", nullable = false)
    private float retailPrice;

    @Positive(message = "Wholesale price must be greater than 0.")
    @Column(name = "wholesalePrice", nullable = false)
    private float wholesalePrice;

    @Min(value = 0, message = "Stock quantity cannot be negative.")
    @Column(name = "stockQuantity", nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductWatchList> productWatchLists = new ArrayList<>();

    public Product(int productId, String name, String description, float retailPrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.retailPrice = retailPrice;
    }
}
