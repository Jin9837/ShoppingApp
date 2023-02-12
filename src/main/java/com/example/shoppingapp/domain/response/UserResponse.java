package com.example.shoppingapp.domain.response;


import com.example.shoppingapp.domain.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String message;
    private User user;
}
