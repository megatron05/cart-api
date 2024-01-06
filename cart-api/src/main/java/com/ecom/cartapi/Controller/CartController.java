package com.ecom.cartapi.Controller;

import com.ecom.cartapi.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestHeader String userId, @RequestHeader String productId, @RequestHeader Integer quantity){
        return cartService.addToCart(userId, productId, quantity);
    }

//    @DeleteMapping("/placeOrder")
//    public ResponseEntity<?> placeOrder (@RequestHeader String cartId){
//        return cartService.placeOrder(cartId);
//    }
}
