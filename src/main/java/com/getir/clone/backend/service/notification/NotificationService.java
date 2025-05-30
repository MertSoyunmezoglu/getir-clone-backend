package com.getir.clone.backend.service.notification;

import com.getir.clone.backend.entity.Order;
import com.getir.clone.backend.entity.User;

public interface NotificationService {
    void sendOrderConfirmation(User user, Order order);
}
