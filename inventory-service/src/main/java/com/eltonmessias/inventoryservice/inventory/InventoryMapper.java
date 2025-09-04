package com.eltonmessias.inventoryservice.inventory;

import com.eltonmessias.inventoryservice.product.ProductResponse;
import com.eltonmessias.inventoryservice.warehouse.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryMapper {

    public Inventory toInventory(InventoryRequest request, Warehouse warehouse, ProductResponse product, UUID tenantId) {


        return Inventory.builder()
                .tenantId(tenantId)
                .productId(product.productId())
                .warehouse(warehouse)
                .quantityAvailable(request.quantityAvailable())
                .quantityPurchased(request.quantityPurchased())
                .quantityIncoming(request.quantityIncoming())
                .reorderPoint(request.reorderPoint())
                .maxStockLevel(request.maxStockLevel())
                .build();
    }

    public InventoryResponse toInventoryResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                inventory.getCode(),
                inventory.getTenantId(),
                inventory.getProductId(),
                inventory.getWarehouse().getId(),
                inventory.getQuantityAvailable(),
                inventory.getQuantityPurchased(),
                inventory.getQuantityIncoming(),
                inventory.getReorderPoint(),
                inventory.getMaxStockLevel(),
                inventory.getLastUpdatedAt()
        );
    }
}
