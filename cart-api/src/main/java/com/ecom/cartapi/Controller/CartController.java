package com.ecom.cartapi.Controller;

import com.ecom.cartapi.DTO.InventoryRequest;
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
    public ResponseEntity<?> addToCart(@RequestHeader Long userId, @RequestBody InventoryRequest inventoryRequest){
        return cartService.addToCart(userId, inventoryRequest);
    }

    @GetMapping("/placeOrder/{cartId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long cartId){
        return cartService.placeOrder(cartId);
    }
}
