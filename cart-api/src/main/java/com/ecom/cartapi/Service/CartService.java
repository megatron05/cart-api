package com.ecom.cartapi.Service;

import com.ecom.cartapi.Builder.CartBuilder;
import com.ecom.cartapi.DTO.InventoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CartService {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CartBuilder cartBuilder;

    @Value("${PRODUCTS_API_URL}")
    private String inventoryUrl;
    public ResponseEntity<?> addToCart(Long userId, InventoryRequest inventoryRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(inventoryRequest, headers);
        InventoryRequest IR = restTemplate.exchange(inventoryUrl, HttpMethod.GET, entity, InventoryRequest.class).getBody();

        if (IR.getQuantity() >= inventoryRequest.getQuantity()){
            cartBuilder.addProductToCart(userId,inventoryRequest);
            return new ResponseEntity<>("Product successfully added to cart",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Only" + IR.getQuantity() +" items left in Inventory", HttpStatus.NOT_FOUND);
        }
    }

//    @Value("${TRANSACTIONS_API_URL}")
//    private String transactionsUrl;
//    @Value("${INVENTORY_REMOVE_API}")
//    private String inventoryRemoveUrl;


//    public ResponseEntity<?> placeOrder(String cartId) {
//        Cart cart = cartRepo.findById(cartId).get();
//        Product product = cart.getProduct();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.set("productId", product.getProductId());
//        headers.set("quantity", Integer.toString(cart.getProductQuantity()));
//        HttpEntity<String> inventoryRemoveEntity = new HttpEntity<>(headers);
//        Product product1 = restTemplate.exchange(inventoryUrl, HttpMethod.GET, inventoryRemoveEntity, Product.class).getBody();
//
//        assert product1 != null;
//        if (product1.getIsPresentInInventory() == Boolean.FALSE) {
//            return new ResponseEntity<>("Product is not available in Inventory", HttpStatus.NOT_FOUND);
//        }
//
//        HttpEntity<Cart> addToTransactionEntity = new HttpEntity<>(cart);
//        return new ResponseEntity<>(restTemplate.exchange(transactionsUrl, HttpMethod.POST, addToTransactionEntity, Boolean.class).getStatusCode());
//
//    }
}