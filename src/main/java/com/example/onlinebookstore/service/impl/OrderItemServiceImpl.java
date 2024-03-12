package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.example.onlinebookstore.service.OrderItemService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId) {
        Set<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        return orderItems
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long itemId) {
        return orderItemRepository.findById(itemId)
                .map(orderItemMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order item by id "
                                + itemId));
    }
}
