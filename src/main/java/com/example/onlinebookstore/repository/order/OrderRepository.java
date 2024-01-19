package com.example.onlinebookstore.repository.order;

import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByUser(User user);
}
