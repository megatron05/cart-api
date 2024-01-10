package com.ecom.cartapi.Builder;


import com.ecom.cartapi.DAO.CartRepo;
import com.ecom.cartapi.DTO.InventoryRequest;
import com.ecom.cartapi.Model.Cart;
import com.ecom.cartapi.Model.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Setter@Getter
@Component
public class CartBuilder {

    @Autowired
    CartRepo cartRepo;
    @Autowired
    ProductBuilder productBuilder;

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

        //TODO check products with Product ID and replace the quantities
        List<InventoryRequest> ir = cart.getProductQuantity();
        ir.add(inventoryRequest);
        try {
            if (products.contains(product)){
                cart.setQuantity(quantity);
            }
            else{
                products.add(product);
                cart.setQuantity(quantity);
            }
        }
        catch (Exception e){
            products.add(product);
            cart.setQuantity(quantity);
        }


        cart.setProducts(products);
        cartRepo.save(cart);
    }
}
