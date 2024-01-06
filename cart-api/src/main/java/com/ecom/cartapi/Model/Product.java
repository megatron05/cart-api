package com.ecom.cartapi.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String productId;
    private String skuCode;
    private String productName;
    private BigDecimal price;
    private String gender;
    private String size;
    private String colour;
    private String type;
}
