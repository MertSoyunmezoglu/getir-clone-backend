package com.getir.clone.backend.entity;

import com.getir.clone.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal totalPrice;
    private BigDecimal totalDiscountedPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // helper
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
