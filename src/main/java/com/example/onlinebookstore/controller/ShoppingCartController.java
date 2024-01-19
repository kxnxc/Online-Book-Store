package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.cartitem.CartItemRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.onlinebookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get users shopping cart",
            description = "Returns existing user's shopping cart or create new if doesn't exit")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping()
    public ShoppingCartResponseDto getUsersShoppingCart(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(userDetails.getUsername());
    }

    @Operation(summary = "Add book to user's shopping cart")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping()
    public ShoppingCartResponseDto addBookToCart(Authentication authentication,
                                                 @RequestBody CartItemRequestDto requestDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return shoppingCartService.addToCart(userDetails.getUsername(), requestDto);
    }

    @Operation(summary = "Update book's quantity in existing cart item")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartResponseDto updateBookQuantity(@PathVariable Long cartItemId,
                                                      @RequestBody CartItemRequestDto requestDto) {
        return shoppingCartService.updateQuantity(cartItemId, requestDto);
    }

    @Operation(summary = "Delete existing cart item from user's shopping cart")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/cart-items/{cartItemId}")
    public void deleteBookFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.deleteCartItemById(cartItemId);
    }
}
