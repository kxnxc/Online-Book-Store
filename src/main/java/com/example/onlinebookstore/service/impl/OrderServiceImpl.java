package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.cartitem.CartItemRepository;
import com.example.onlinebookstore.repository.order.OrderRepository;
import com.example.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.example.onlinebookstore.repository.user.UserRepository;
import com.example.onlinebookstore.service.OrderService;
import com.example.onlinebookstore.service.ShoppingCartService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(String email, OrderRequestDto requestDto) {
        User user = userRepository.getByEmail(email);
        ShoppingCart shoppingCart = shoppingCartService.getInitializedCartEntity(email);
        Order order = new Order();
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(getTotalPrice(shoppingCart));
        orderRepository.save(order);
        order.setOrderItems(createOrderItems(shoppingCart, order));
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getOrderHistory(String email) {
        User user = userRepository.getByEmail(email);
        return orderRepository.getAllByUser(user)
                .stream().map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, OrderStatusRequestDto requestDto) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order with id "
                                + id));
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    private BigDecimal getTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart
                .getCartItems()
                .stream()
                .map(cartItem -> {
                    BigDecimal bookPrice = cartItem.getBook().getPrice();
                    BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
                    return bookPrice.multiply(quantity);
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> createOrderItems(ShoppingCart shoppingCart,
                                            Order order) {
        Order mockOrder = new Order();
        mockOrder.setId(order.getId());
        return shoppingCart
                .getCartItems()
                .stream()
                .map(cartItem -> orderItemRepository.save(OrderItem.builder()
                        .book(cartItem.getBook())
                        .price(cartItem.getBook().getPrice())
                        .quantity(cartItem.getQuantity())
                        .order(mockOrder)
                        .build()))
                .collect(Collectors.toSet());
    }
}
