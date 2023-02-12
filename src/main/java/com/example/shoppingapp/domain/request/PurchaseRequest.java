package com.example.shoppingapp.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {
    private int productId;
    private int userId;
    private int quantity;
}
