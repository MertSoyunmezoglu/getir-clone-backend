package com.getir.clone.backend.controller;

import com.getir.clone.backend.dto.request.CartItemRequest;
import com.getir.clone.backend.dto.response.CartDTO;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.service.interfaces.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Sepet işlemleri")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Sepeti getir")
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PostMapping("/items")
    @Operation(summary = "Sepete ürün ekle")
    public ResponseEntity<CartDTO> addItem(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(user, request));
    }

    @PutMapping("/items")
    @Operation(summary = "Sepet ürününü güncelle")
    public ResponseEntity<CartDTO> updateItem(@AuthenticationPrincipal User user,
                                              @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.updateItem(user, request));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Sepetten ürün çıkar")
    public ResponseEntity<CartDTO> removeItem(@AuthenticationPrincipal User user,
                                              @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItem(user, productId));
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Sepeti temizle")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user);
        return ResponseEntity.ok().build();
    }
}
