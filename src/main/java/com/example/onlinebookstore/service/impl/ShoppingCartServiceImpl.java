package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CartItemMapper;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.book.BookRepository;
import com.example.onlinebookstore.repository.cartitem.CartItemRepository;
import com.example.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import com.example.onlinebookstore.repository.user.UserRepository;
import com.example.onlinebookstore.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getShoppingCart(String email) {
        ShoppingCart shoppingCart = getCartOrCreate(email);
        shoppingCart.setCartItems(cartItemRepository.getAllByShoppingCart(shoppingCart));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addToCart(String email, CartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getCartOrCreate(email);
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(bookRepository.getById(requestDto.getBookId()));
        cartItemRepository.save(cartItem);
        shoppingCart.setCartItems(cartItemRepository.getAllByShoppingCart(shoppingCart));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateQuantity(Long id, CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find cart item with id " + id));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = cartItem.getShoppingCart();
        shoppingCart.setCartItems(cartItemRepository.getAllByShoppingCart(shoppingCart));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }

    private ShoppingCart getCartOrCreate(String email) {
        User user = userRepository.getByEmail(email);
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository
                .findByUser(user);
        return optionalShoppingCart
                .orElseGet(() -> createCart(user));
    }

    private ShoppingCart createCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }
}
