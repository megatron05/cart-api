package com.ecom.cartapi.Builder;


import com.ecom.cartapi.DAO.CartRepo;
import com.ecom.cartapi.Model.CartObject;
import com.ecom.cartapi.Model.Cart;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Setter@Getter
@Component
public class CartBuilder {

    @Autowired
    CartRepo cartRepo;

    public Cart findUserCart(Long userId){
        if (cartRepo.findByUserId(userId).isEmpty()){
            return Cart.builder()
                    .cartId(userId)
                    .userId(userId)
                    .ProductQuantity(new ArrayList<>())
                    .build();
        }else{
            return cartRepo.findByUserId(userId).get();
        }
    }
    public void addProductToCart(Long userId, CartObject cartObject){
        Cart cart = findUserCart(userId);
        List<CartObject> ir = cart.getProductQuantity();
        Optional<CartObject> existingRequest = ir.stream()
                .filter(request -> request.getProductId().equals(cartObject.getProductId()))
                .findFirst();

        if (existingRequest.isPresent()) {
            CartObject existing = existingRequest.get();
            existing.setQuantity(cartObject.getQuantity());
        } else {
            ir.add(cartObject);
        }
        cart.setProductQuantity(ir);
        cartRepo.save(cart);
    }
}
