package com.ecom.cartapi.Service;

import com.ecom.cartapi.Builder.CartBuilder;
import com.ecom.cartapi.DTO.ProductQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class CartService {


    @Autowired
    RestTemplate restTemplate;
//    @Autowired
//    Gson gson;
    @Autowired
CartBuilder cartBuilder;

//    @Value("${PRODUCTS_API_URL}")
//    private String inventoryUrl;
//    public ResponseEntity<?> addToCart(String userId, String productQuantityJson) {
//
//        Map<String, Integer> proQt = gson.fromJson(productQuantityJson, Map.class);
//        Map<String, Integer> counter = null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> entity = new HttpEntity<>(productQuantityJson, headers);
//        List<ProductQuantity> productQuantities = restTemplate.exchange(inventoryUrl, HttpMethod.GET, entity, List.class).getBody();
//
//        productQuantities.forEach(productQuantity -> {
//            if (productQuantity.getIsPresentInInventory())
//                cart.addProductToCart(productQuantity, userId);
//            else counter.put(productQuantity.getProductId(), productQuantity.getAvailableQuantity());} );
//
//        if (counter.isEmpty()){
//            return new ResponseEntity<>("All Products Successfully added to the Cart", HttpStatus.OK);
//        }
//        else {
//            counter.forEach();
//            return new ResponseEntity<>(String.valueOf(counter), HttpStatus.MULTI_STATUS);
//        }
//    }


    @Value("${PRODUCTS_API_URL}")
    private String inventoryUrl;
    public ResponseEntity<?> addToCart(String userId, String productId, Integer quantity) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("productId", productId);
        headers.set("quantity", String.valueOf(quantity));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ProductQuantity productQuantity = restTemplate.exchange(inventoryUrl, HttpMethod.GET, entity, ProductQuantity.class).getBody();

        if (productQuantity.getAvailableQuantity() >= quantity){
            cartBuilder.addProductToCart(productQuantity, userId, quantity);
            return new ResponseEntity<>("Product Succesfully added to your cart", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Only " + productQuantity.getAvailableQuantity() + " items available in Inventory", HttpStatus.OK);
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