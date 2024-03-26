package com.ecom.cartapi.Controller;

import com.ecom.cartapi.DTO.InventoryRequest;
import com.ecom.cartapi.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestHeader Long userId, @RequestBody InventoryRequest inventoryRequest){
        return cartService.addToCart(userId, inventoryRequest);
    }

    @GetMapping("/getCart")
    public ResponseEntity<?> getCart(@RequestHeader Long userId){
        return cartService.getCartObjects(userId);
    }

//    @PostMapping("/updateCart")
//    public ResponseEntity<?> updateCart(@RequestHeader Long userId){
//        return cartService.updateCart(userId);
//    }

    @DeleteMapping("/removeItem")
    public ResponseEntity<?> removeItem (@RequestHeader Long userId, @RequestHeader ArrayList<Long> productIds){
        return cartService.removeItem(userId, productIds);
    }
//    @DeleteMapping("/placeOrder")
//    public ResponseEntity<?> placeOrder (@RequestHeader String cartId){
//        return cartService.placeOrder(cartId);
//    }
}
