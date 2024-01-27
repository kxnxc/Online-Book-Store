package com.example.onlinebookstore.repository.order;

import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> getAllByUser(User user);

    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findById(Long id);
}
