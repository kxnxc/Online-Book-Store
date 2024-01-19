package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.cartitem.CartItemRepository;
import com.example.onlinebookstore.repository.order.OrderRepository;
import com.example.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.example.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import com.example.onlinebookstore.repository.user.UserRepository;
import com.example.onlinebookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto createOrder(String email, OrderRequestDto requestDto) {
        User user = userRepository.getByEmail(email);
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUser(user).orElseThrow(() ->
                        new EntityNotFoundException("Can't create order "
                                + "without existing shopping cart"));
        Order order = new Order();
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(getTotalPrice(shoppingCart));
        order.setOrderItems(createOrderItems(shoppingCart, order));
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getOrderHistory(String email) {
        User user = userRepository.getByEmail(email);
        List<Order> orders = orderRepository.getAllByUser(user);
        return orders
                .stream()
                .map(order -> {
                    order.setOrderItems(orderItemRepository.findAllByOrder(order));
                    return order;
                }).map(orderMapper::toDto).toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, OrderStatusRequestDto requestDto) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order with id "
                                + id));
        Order.Status updatedStatus = null;
        for (Order.Status s: Order.Status.values()) {
            if (s.toString().equalsIgnoreCase(requestDto.getStatus())) {
                updatedStatus = s;
            }
        }
        if (updatedStatus == null) {
            throw new RuntimeException("Can't find status with name "
                    + requestDto.getStatus());
        }
        order.setStatus(updatedStatus);
        orderRepository.save(order);
        order.setOrderItems(orderItemRepository.findAllByOrder(order));
        return orderMapper.toDto(order);
    }

    private BigDecimal getTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem: shoppingCart.getCartItems()) {
            BigDecimal bookPrice = cartItem.getBook().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
            totalPrice = totalPrice.add(bookPrice.multiply(quantity));
        }
        return totalPrice;
    }

    private Set<OrderItem> createOrderItems(ShoppingCart shoppingCart,
                                            Order order) {
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem: cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItemRepository.save(orderItem));
        }
        return orderItems;
    }
}
