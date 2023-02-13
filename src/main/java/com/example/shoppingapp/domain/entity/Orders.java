package com.example.shoppingapp.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", unique = true, nullable = false)
    private int orderId;
    @Column(name = "userId", nullable = false)
    private int userId;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "userId", insertable = false, updatable = false)
    //    private User user;
    @Column(name = "orderStatus", nullable = false)
    private String orderStatus;

    @Column(name = "datePlaced", nullable = false)
    private Timestamp datePlaced;
}
