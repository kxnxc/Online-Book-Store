package com.example.onlinebookstore.dto.order;

import com.example.onlinebookstore.model.Order;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderStatusRequestDto {
    @NotEmpty
    private Order.Status status;
}
