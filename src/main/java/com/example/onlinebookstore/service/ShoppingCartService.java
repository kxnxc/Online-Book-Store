package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(String email);

    ShoppingCartResponseDto addToCart(String email, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateQuantity(Long id, CartItemRequestDto requestDto);

    void deleteCartItemById(Long id);

    ShoppingCart getInitializedCartEntity(String email);
}
