package com.ecom.cartapi.DAO;

import com.ecom.cartapi.Model.CartObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartObjectRepo extends JpaRepository<CartObject, Long> {
    Optional<CartObject> findByProductId(Long productId);
}
