package com.eltonmessias.product.product;

import com.eltonmessias.product.category.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        UUID tenantId,
        String sku,
        String name,
        String description,
        Category category,
        String brand,
        BigDecimal unitPrice,
        BigDecimal costPrice,
        Float weight,
        Float dimensionsLength,
        Float dimensionsWidth,
        Float dimensionsHeight,
        STATUS status,
        Boolean isSerialized,
        Boolean requiresShipping,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
