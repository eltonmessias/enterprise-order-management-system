package com.eltonmessias.inventoryservice.inventory;

import com.eltonmessias.inventoryservice.Tenant.TenantContext;
import com.eltonmessias.inventoryservice.exception.InventoryNotFoundException;
import com.eltonmessias.inventoryservice.exception.WarehouseNotFoundException;
import com.eltonmessias.inventoryservice.product.ProductClient;
import com.eltonmessias.inventoryservice.product.ProductResponse;
import com.eltonmessias.inventoryservice.warehouse.Warehouse;
import com.eltonmessias.inventoryservice.warehouse.WarehouseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;
    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductClient productClient;

    public InventoryResponse createInventory(@Valid InventoryRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        Warehouse warehouse = warehouseRepository.findByIdAndTenantId(request.warehouseId(), tenantId).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        ProductResponse product = productClient.getProductById(request.productId());
        var inventory = inventoryMapper.toInventory(request, warehouse, product, tenantId);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventory));
    }

    public PagedResponse<InventoryResponse> getAllInventories(Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        Page<Inventory> inventory = inventoryRepository.findAllByTenantId(tenantId);
        Page<InventoryResponse> responsePage = inventory.map(inventoryMapper::toInventoryResponse);
        return new PagedResponse<>(responsePage);
    }

    public InventoryResponse getInventoryById(UUID inventoryId) {
        UUID tenantId = TenantContext.getTenantId();
        var inventory = inventoryRepository.findByIdAndTenantId(inventoryId, tenantId).orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));
        return inventoryMapper.toInventoryResponse(inventory);
    }
}
