package com.ecom.cartapi.Model;


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
    private String cartId;
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;
    private Integer quantity;

}
