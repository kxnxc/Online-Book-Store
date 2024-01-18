package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(String email);

    ShoppingCartResponseDto addToCart(String email, CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateQuantity(Long id, CartItemRequestDto requestDto);

    void deleteCartItemById(Long id);
}
