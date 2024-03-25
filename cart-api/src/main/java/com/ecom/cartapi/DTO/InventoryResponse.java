package com.ecom.cartapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Long productId;
    private Integer requestedQuantity;
    private Integer existingQuantity;
    private Boolean isInStock;
}
