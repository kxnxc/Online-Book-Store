package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId);

    OrderItemResponseDto getOrderItemById(Long itemId);
}
