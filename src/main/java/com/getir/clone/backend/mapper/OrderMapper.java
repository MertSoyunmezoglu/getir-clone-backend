package com.getir.clone.backend.mapper;


import com.getir.clone.backend.dto.response.OrderDTO;
import com.getir.clone.backend.dto.response.OrderItemDTO;
import com.getir.clone.backend.entity.Order;
import com.getir.clone.backend.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "status", source = "status")
    OrderDTO toOrderDTO(Order order);

    OrderItemDTO toOrderItemDTO(OrderItem orderItem);
    List<OrderDTO> toOrderDTOs(List<Order> orders);
    List<OrderItemDTO> toOrderItemDTOs(List<OrderItem> items);
}

