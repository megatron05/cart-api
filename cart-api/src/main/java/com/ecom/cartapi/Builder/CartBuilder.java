package com.ecom.cartapi.Builder;


import com.ecom.cartapi.DAO.CartRepo;
import com.ecom.cartapi.DTO.ProductQuantity;
import com.ecom.cartapi.Model.Cart;
import com.ecom.cartapi.Model.Product;
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
    @Autowired
    ProductBuilder productBuilder;

    public void addProductToCart(ProductQuantity productQuantity, String userId, Integer quantity){
        final Product product = productBuilder.buildProduct(productQuantity);
        final Cart cart;
        if (cartRepo.findByUserId(userId).isEmpty()){
            cart = Cart.builder()
                    .cartId(userId)
                    .userId(userId)
                    .products(new ArrayList<>())
                    .quantity(null)
                    .build();
        }
        else{
            cart = cartRepo.findByUserId(userId).get();
        }
        List<Product> products = cart.getProducts();

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
