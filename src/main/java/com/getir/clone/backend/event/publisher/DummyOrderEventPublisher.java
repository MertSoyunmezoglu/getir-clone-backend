package com.getir.clone.backend.event.publisher;

import com.getir.clone.backend.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DummyOrderEventPublisher implements OrderEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(DummyOrderEventPublisher.class);

    @Override
    public void publishOrderCreatedEvent(Order order) {
        log.info("Order created event published for orderId: {}", order.getId());
    }
}
