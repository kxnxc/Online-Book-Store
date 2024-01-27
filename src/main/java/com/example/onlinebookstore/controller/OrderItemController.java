package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order item management api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders/{orderId}/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Operation(summary = "Get all order items for order",
            description = "Get all order items by order id")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping()
    public List<OrderItemResponseDto> getAllOrderItemsForOrder(@PathVariable
                                                                   Long orderId) {
        return orderItemService.getAllOrderItemsByOrderId(orderId);
    }

    @Operation(summary = "Get specific order item by id",
            description = "Get specific order item by id")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/{itemId}")
    public OrderItemResponseDto getSpecificOrderItem(@PathVariable Long itemId) {
        return orderItemService.getOrderItemById(itemId);
    }
}
