package com.ecom.cartapi.DAO;

import com.ecom.cartapi.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, String> {
}
