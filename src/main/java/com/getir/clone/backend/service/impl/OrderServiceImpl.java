package com.getir.clone.backend.service.impl;


import com.getir.clone.backend.dto.response.OrderDTO;
import com.getir.clone.backend.entity.Cart;
import com.getir.clone.backend.entity.CartItem;
import com.getir.clone.backend.entity.Order;
import com.getir.clone.backend.entity.User;
import com.getir.clone.backend.enums.OrderStatus;
import com.getir.clone.backend.event.publisher.OrderEventPublisher;
import com.getir.clone.backend.mapper.OrderMapper;
import com.getir.clone.backend.repository.OrderRepository;
import com.getir.clone.backend.service.interfaces.CartService;
import com.getir.clone.backend.service.interfaces.OrderService;
import com.getir.clone.backend.service.notification.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OrderEventPublisher orderEventPublisher;
    private final NotificationService notificationService;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartService cartService,
                            OrderEventPublisher orderEventPublisher,
                            NotificationService notificationService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderEventPublisher = orderEventPublisher;
        this.notificationService = notificationService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDTO createOrderFromCart(User user) {
        Cart cart = cartService.getActiveCartEntity(user);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty or not found!");
        }

        Order order = getOrder(user, cart);
        Order savedOrder = orderRepository.save(order);
        cartService.checkout(cart);

        orderEventPublisher.publishOrderCreatedEvent(savedOrder);
        notificationService.sendOrderConfirmation(user, savedOrder);

        return orderMapper.toOrderDTO(savedOrder);
    }

    private static Order getOrder(User user, Cart cart) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalDiscounted = BigDecimal.ZERO;
        for (CartItem ci : cart.getItems()) {
            BigDecimal price = ci.getProduct().getPrice();
            BigDecimal discounted = ci.getProduct().getDiscountedPrice();
            int qty = ci.getQuantity();

            total = total.add(price.multiply(BigDecimal.valueOf(qty)).setScale(2, RoundingMode.HALF_UP));
            totalDiscounted = totalDiscounted.add(discounted.multiply(BigDecimal.valueOf(qty)).setScale(2, RoundingMode.HALF_UP));
        }
        order.setTotalPrice(total.setScale(2, RoundingMode.HALF_UP));
        order.setTotalDiscountedPrice(totalDiscounted.setScale(2, RoundingMode.HALF_UP));
        return order;
    }

    @Override
    public List<OrderDTO> getUserOrders(User user) {
        return orderMapper.toOrderDTOs(orderRepository.findByUserId(user.getId()));

    }

    @Override
    public OrderDTO getOrderDetail(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Unauthorized access to this order!");
        }
        return orderMapper.toOrderDTO(order);
    }

}
