package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(String email, OrderRequestDto requestDto);

    List<OrderResponseDto> getOrderHistory(String email);

    OrderResponseDto updateOrderStatus(Long id, OrderStatusRequestDto requestDto);
}
