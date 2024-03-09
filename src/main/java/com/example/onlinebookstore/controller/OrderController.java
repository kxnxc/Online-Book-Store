package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import com.example.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get order history",
            description = "Get order history for authenticated user")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping()
    public List<OrderResponseDto> getOrderHistory(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return orderService.getOrderHistory(userDetails.getUsername());
    }

    @Operation(summary = "Create new order",
            description = "Create new order (authenticate needed)")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping()
    public OrderResponseDto createOrder(Authentication authentication,
                                        @RequestBody OrderRequestDto requestDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return orderService.createOrder(userDetails.getUsername(), requestDto);
    }

    @Operation(summary = "Update order status",
            description = "Update order status in existing order, admin role needed")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public OrderResponseDto updateOrderStatus(@PathVariable Long id,
                                              @RequestBody OrderStatusRequestDto
                                                      requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }
}
