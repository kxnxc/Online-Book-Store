package com.example.onlinebookstore.dto.cartitem;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long bookId;
    private Integer quantity;
}
