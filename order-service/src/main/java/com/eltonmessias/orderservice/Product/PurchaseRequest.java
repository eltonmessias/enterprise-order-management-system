package com.eltonmessias.orderservice.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record PurchaseRequest(
        @NotNull(message = "Product is mandatory")
        UUID productId,
        @Positive(message = "Quantity is mandatory")
        int quantity
) {
}
