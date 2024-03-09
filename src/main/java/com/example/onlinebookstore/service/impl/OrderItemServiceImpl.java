package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.example.onlinebookstore.service.OrderItemService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    private final Consumer<OrderItem> setOrder = oi -> oi.setOrder(null);

    @Override
    public List<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId) {
        Set<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        return orderItems
                .stream()
                .peek(oi -> oi.setOrder(null))
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long itemId) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(itemId);
        optionalOrderItem.ifPresent(setOrder);
        return optionalOrderItem
                .map(orderItemMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order item by id "
                                + itemId));
    }
}
