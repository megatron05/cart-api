package com.ecom.cartapi.DTO;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private Long productId;
    private Integer quantity;
}
