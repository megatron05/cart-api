package com.ecom.cartapi.Builder;

import com.ecom.cartapi.DTO.ProductQuantity;
import com.ecom.cartapi.Model.Product;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Data
@Getter
@Setter
@Builder
@Component
public class ProductBuilder {

    public Product buildProduct(ProductQuantity productQuantity) {
       return Product.builder()
                .productId(productQuantity.getProductId())
                .skuCode(productQuantity.getSkuCode())
                .productName(productQuantity.getProductName())
                .price(productQuantity.getPrice())
                .type(productQuantity.getType())
                .size(productQuantity.getSize())
                .gender(productQuantity.getGender())
                .colour(productQuantity.getColour()).build();
    }
}
