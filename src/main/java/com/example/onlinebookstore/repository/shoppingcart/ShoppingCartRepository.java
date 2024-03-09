package com.example.onlinebookstore.repository.shoppingcart;

import com.example.onlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = "cartItems")
    Optional<ShoppingCart> findByUser_Id(Long id);

    @Query(
            """
            SELECT sc FROM ShoppingCart sc 
            LEFT JOIN FETCH sc.cartItems c WHERE sc.user.id = :id
            """
    )

    Optional<ShoppingCart> findById(Long id);
}
