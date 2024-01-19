package com.example.onlinebookstore.repository.cartitem;

import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> getAllByShoppingCart(ShoppingCart shoppingCart);
}
