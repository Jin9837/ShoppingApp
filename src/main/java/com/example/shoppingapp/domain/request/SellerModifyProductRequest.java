package com.example.shoppingapp.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerModifyProductRequest {
    private int productId;
    private String name;
    private String description;
    private float retailPrice;
    private float wholesalePrice;
    private int stockQuantity;
}
