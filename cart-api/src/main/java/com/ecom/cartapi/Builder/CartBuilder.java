package com.ecom.cartapi.Builder;


import com.ecom.cartapi.DAO.CartRepo;
import com.ecom.cartapi.DTO.InventoryRequest;
import com.ecom.cartapi.Model.Cart;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter@Getter
@Component
public class CartBuilder {

    @Autowired
    CartRepo cartRepo;

    public void addProductToCart(Long userId, InventoryRequest inventoryRequest){
        final Cart cart;
        if (cartRepo.findByUserId(userId).isEmpty()){
            cart = Cart.builder()
                    .cartId(userId)
                    .userId(userId)
                    .ProductQuantity(new ArrayList<>())
                    .build();
        }
        else{
            cart = cartRepo.findByUserId(userId).get();
        }
        List<InventoryRequest> ir = cart.getProductQuantity();
        ir.forEach(invRq1 -> invRq1.setQuantity(invRq1.getProductId() == inventoryRequest.getProductId() ? invRq1.getQuantity()+inventoryRequest.getQuantity(): inventoryRequest.getQuantity()));
        cart.setProductQuantity(ir);
        cartRepo.save(cart);
    }
}
