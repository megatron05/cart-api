package com.ecom.cartapi.Service;

import com.ecom.cartapi.Builder.CartBuilder;
import com.ecom.cartapi.DAO.CartRepo;
import com.ecom.cartapi.DTO.InventoryRequest;
import com.ecom.cartapi.DTO.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
public class CartService {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CartBuilder cartBuilder;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    CartRepo cartRepo;

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

    public ResponseEntity<?> placeOrder(Long cartId) {
        List<InventoryRequest> inventoryRequests = (cartRepo.findById(cartId).get()).getProductQuantity();

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().post()
                .uri("http://localhost:8083/inventory/check")
                .bodyValue(inventoryRequests)
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(inventoryResponse -> inventoryResponse.getIsInStock());

        if (allProductsInStock){
            // need to call transactions API / Payments API
            return new ResponseEntity<>("All products are in Stock", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(inventoryResponseArray, HttpStatus.CONFLICT);
        }

    }

}