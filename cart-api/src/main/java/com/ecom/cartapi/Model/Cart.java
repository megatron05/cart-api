package com.ecom.cartapi.Model;


import com.ecom.cartapi.DTO.InventoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Data
@Entity
@CrossOrigin
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cart")
public class Cart {

    @Id
    private Long cartId;
    private Long userId;
    @ElementCollection
    private List<InventoryRequest> ProductQuantity;


}
