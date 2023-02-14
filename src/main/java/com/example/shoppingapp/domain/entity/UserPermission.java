package com.example.shoppingapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="UserPermission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userPermissionId", unique = true, nullable = false)
    private int userPermissionId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "permissionId", nullable = false)
    private Permissions permission;
}
