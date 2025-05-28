package com.getir.clone.backend.repository;

import com.getir.clone.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndCheckedOutFalse(Long userId);
}