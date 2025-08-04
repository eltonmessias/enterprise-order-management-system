package com.eltonmessias.product.product;

import com.eltonmessias.product.category.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private UUID tenantId;
    @Column(unique = true, nullable = false)
    private String sku;
    @NotNull(message = "The name is mandatory")
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String brand;
    private BigDecimal unitPrice;
    private BigDecimal costPrice;
    private BigDecimal weight;
    private BigDecimal dimensionsLength;
    private BigDecimal dimensionsWidth;
    private BigDecimal dimensionsHeight;
    @Enumerated(EnumType.STRING)
    private STATUS status = STATUS.ACTIVE;
    private Boolean isSerialized  = false;
    private Boolean requiresShipping = true;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


}
