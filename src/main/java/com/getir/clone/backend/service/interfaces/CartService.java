package com.getir.clone.backend.service.interfaces;


import com.getir.clone.backend.dto.request.CartItemRequest;
import com.getir.clone.backend.dto.response.CartDTO;
import com.getir.clone.backend.entity.User;

public interface CartService {
    CartDTO getCart(User user);
    CartDTO addItem(User user, CartItemRequest request);
    CartDTO updateItem(User user, CartItemRequest request);
    CartDTO removeItem(User user, Long productId);
    CartDTO checkout(User user);
    void clearCart(User user);
}
