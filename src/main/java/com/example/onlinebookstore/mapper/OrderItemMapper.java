package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.config.MapperConfig;
import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.model.OrderItem;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Named("getOrderItemDtos")
    default Set<OrderItemResponseDto> getOrderItemDtos(Set<OrderItem> orderItems) {
        Set<OrderItemResponseDto> orderItemResponseDtos = new HashSet<>();
        for (OrderItem orderItem: orderItems) {
            orderItemResponseDtos.add(toDto(orderItem));
        }
        return orderItemResponseDtos;
    }
}
