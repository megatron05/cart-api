package com.ecom.cartapi.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    @Id
    private Long productId;
    private Integer quantity;
}
