package com.example.shoppingapp.domain.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProcessingOrderBySeller {
    private int orderId;
    private String newStatus;
}
