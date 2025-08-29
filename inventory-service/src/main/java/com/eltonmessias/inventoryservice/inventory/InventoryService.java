package com.eltonmessias.inventoryservice.inventory;

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
        Warehouse warehouse = warehouseRepository.findById(request.warehouseId()).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        ProductResponse product = productClient.getProductById(request.productId());
        var inventory = inventoryMapper.toInventory(request, warehouse, product);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventory));
    }

    public PagedResponse<InventoryResponse> getAllInventories(Pageable pageable) {
        Page<Inventory> inventory = inventoryRepository.findAll(pageable);
        Page<InventoryResponse> responsePage = inventory.map(inventoryMapper::toInventoryResponse);
        return new PagedResponse<>(responsePage);
    }

    public InventoryResponse getInventoryById(UUID inventoryId) {
        var inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));
        return inventoryMapper.toInventoryResponse(inventory);
    }
}
