package com.eltonmessias.inventoryservice.inventory;

import com.eltonmessias.inventoryservice.warehouse.Warehouse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String code;
    @NotNull(message = "The tenant-id is mandatory")
    private UUID tenantId;
    @NotNull(message = "The product-id is mandatory")
    private UUID productId;
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
    private Integer quantityAvailable = 0;
    private Integer quantityPurchased = 0;
    private Integer quantityIncoming = 0;
    private Integer reorderPoint = 0;
    private Integer maxStockLevel;
    private LocalDateTime lastUpdatedAt;


    @PreUpdate
    public void preUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void generateCode() {
        if(this.code == null || this.code.isBlank() ){
            this.code = "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }


}
