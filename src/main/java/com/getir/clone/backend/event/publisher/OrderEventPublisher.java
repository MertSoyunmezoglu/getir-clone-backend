package com.getir.clone.backend.event.publisher;

import com.getir.clone.backend.entity.Order;

public interface OrderEventPublisher {
    void publishOrderCreatedEvent(Order order);
}

