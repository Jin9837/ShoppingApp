package com.example.shoppingapp.domain.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductWatchListRequest {
    private int userId;
    private int productId;
}
