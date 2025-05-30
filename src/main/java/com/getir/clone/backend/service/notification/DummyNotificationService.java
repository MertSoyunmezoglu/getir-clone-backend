package com.getir.clone.backend.service.notification;

import com.getir.clone.backend.entity.Order;
import com.getir.clone.backend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DummyNotificationService implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(DummyNotificationService.class);

    @Override
    public void sendOrderConfirmation(User user, Order order) {
        log.info("Order confirmation notification sent to user: {} for orderId: {}", user.getEmail(), order.getId());
    }
}
