package com.example.shoppingapp.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="ProductWatchList")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductWatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productWatchListId", unique = true, nullable = false)
    private int productWatchListId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)
    private Product product;
}
