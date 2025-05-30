package com.getir.clone.backend.controller;

import com.getir.clone.backend.dto.response.OrderDTO;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.service.interfaces.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order operations")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Create order from active cart (checkout)")
    public ResponseEntity<OrderDTO> createOrder(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.createOrderFromCart(user));
    }

    @GetMapping
    @Operation(summary = "Get user's orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order detail")
    public ResponseEntity<OrderDTO> getOrderDetail(@AuthenticationPrincipal User user,
                                                   @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetail(user, orderId));
    }
}

