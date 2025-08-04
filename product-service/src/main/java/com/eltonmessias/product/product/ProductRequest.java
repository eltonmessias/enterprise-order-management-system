package com.eltonmessias.product.product;

import com.eltonmessias.product.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductRequest(
        @NotBlank(message = "The tenantId is mandatory") UUID tenantId,
        @NotBlank(message = "The sku is mandatory") String sku,
        @NotBlank(message = "The name is mandatory") String name,
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
        Boolean requiresShipping
) {
}
