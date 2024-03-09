package com.example.onlinebookstore.repository.orderitem;

import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Set<OrderItem> findAllByOrder(Order order);

    Set<OrderItem> findAllByOrderId(Long orderId);
}
