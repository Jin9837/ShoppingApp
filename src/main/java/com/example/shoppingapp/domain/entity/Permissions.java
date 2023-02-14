package com.example.shoppingapp.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionId", unique = true, nullable = false)
    private int permissionId;
    @Column(name = "permissionRole", nullable = false)
    private String permissionRole;

}
