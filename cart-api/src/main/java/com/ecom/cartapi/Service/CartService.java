package com.ecom.cartapi.Service;

import com.ecom.cartapi.Builder.CartBuilder;
import com.ecom.cartapi.DAO.CartObjectRepo;
import com.ecom.cartapi.DAO.CartRepo;
import com.ecom.cartapi.Model.Cart;
import com.ecom.cartapi.Model.CartObject;
import com.ecom.cartapi.DTO.InventoryRequest;
import com.ecom.cartapi.DTO.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {


    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WebClient webClient;
    @Autowired
    CartObjectRepo cartObjectRepo;
    @Autowired
    CartBuilder cartBuilder;
    @Autowired
    CartRepo cartRepo;

    @Value("${PRODUCTS_API_URL}")
    private String inventoryUrl;
    public ResponseEntity<?> addToCart(Long userId, InventoryRequest inventoryRequest) {

        List<InventoryRequest> inventoryRequests = new ArrayList<>();
        inventoryRequests.add(inventoryRequest);

        List<InventoryResponse> response = webClient.post()
                .uri(inventoryUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequests)
                .retrieve()
                .bodyToFlux(InventoryResponse.class)
                .collectList()
                .block();

        System.out.println(response);
        if (response.get(0).getExistingQuantity() >= inventoryRequest.getQuantity()){

            CartObject cartObject = new CartObject();

            if (cartObjectRepo.findByProductId(inventoryRequest.getProductId()).isPresent()){
                cartObject = cartObjectRepo.findByProductId(inventoryRequest.getProductId()).get();
                cartObject.setQuantity(inventoryRequest.getQuantity());
            } else {
                cartObject.setProductId(inventoryRequest.getProductId());
                cartObject.setQuantity(inventoryRequest.getQuantity());
                cartObjectRepo.save(cartObject);
            }

            cartBuilder.addProductToCart(userId,cartObject);
            return new ResponseEntity<>("Product successfully added to cart",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Only" +response.get(0).getExistingQuantity() +" items left in Inventory", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getCartObjects(Long userId) {
        Cart cart = cartBuilder.findUserCart(userId);
        cartRepo.save(cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<?> removeItem(Long userId, ArrayList<Long> productIds) {

        Cart cart = cartBuilder.findUserCart(userId);
        List<CartObject> productsList = cart.getProductQuantity();
        productIds.forEach(productId -> {
        Optional<CartObject> product = productsList.stream()
                .filter(request -> request.getProductId().equals(productId))
                .findFirst();
        if (product.isPresent()) {
            productsList.remove(product.get());
            cartObjectRepo.delete(product.get());
        }});
        return new ResponseEntity<>(cart, HttpStatus.OK);
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