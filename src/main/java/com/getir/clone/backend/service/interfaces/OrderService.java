package com.getir.clone.backend.service.interfaces;

import com.getir.clone.backend.dto.response.OrderDTO;
import com.getir.clone.backend.entity.User;

import java.util.List;

public interface OrderService {
    OrderDTO createOrderFromCart(User user);
    List<OrderDTO> getUserOrders(User user);
    OrderDTO getOrderDetail(User user, Long orderId);
}

