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
    @Column(name = "userId", nullable = false)
    private int userId;
    @Column(name = "productId", nullable = false)
    private int productId;
}
