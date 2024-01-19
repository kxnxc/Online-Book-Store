package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.service.OrderItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders/{orderId}/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping()
    public List<OrderItemResponseDto> getAllOrderItemsForOrder(@PathVariable
                                                                   Long orderId) {
        return orderItemService.getAllOrderItemsByOrderId(orderId);
    }

    @GetMapping("/{itemId}")
    public OrderItemResponseDto getSpecificOrderItem(@PathVariable Long itemId) {
        return orderItemService.getOrderItemById(itemId);
    }
}
