package com.eltonmessias.product.product;

import com.eltonmessias.product.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductRequest(
        @NotBlank(message = "The sku is mandatory") String sku,
        @NotBlank(message = "The name is mandatory") String name,
        String description,
        UUID categoryId,
        String brand,
        BigDecimal unitPrice,
        BigDecimal costPrice,
        BigDecimal weight,
        BigDecimal dimensionsLength,
        BigDecimal dimensionsWidth,
        BigDecimal dimensionsHeight,
        STATUS status,
        Boolean isSerialized,
        Boolean requiresShipping
) {
}
