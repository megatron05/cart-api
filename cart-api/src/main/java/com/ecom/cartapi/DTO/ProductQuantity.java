package com.ecom.cartapi.DTO;


import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantity {
    private String productId;
    private String skuCode;
    private String productName;
    private BigDecimal price;
    private String gender;
    private String size;
    private String colour;
    private String type;
    private Integer availableQuantity;
    private Boolean isPresentInInventory;
}
