package com.eltonmessias.inventoryservice.warehouse;

import com.eltonmessias.inventoryservice.Tenant.TenantClient;
import com.eltonmessias.inventoryservice.exception.BusinessException;
import com.eltonmessias.inventoryservice.exception.WarehouseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final TenantClient tenantClient;
    private final WarehouseMapper warehouseMapper;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public UUID createWarehouse(WarehouseRequest request) {
        var tenant = this.tenantClient.getTenantById(request.tenantId())
                .orElseThrow(() -> new BusinessException("Cannot create warehouse:: No tenant exists with tenantId " +
                        "the provided ID"));

        var warehouse = warehouseMapper.toWarehouse(request, tenant);
        return warehouseRepository.save(warehouse).getId();

    }

    public List<WarehouseResponse> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream().map(warehouseMapper::toWarehouseResponse).collect(Collectors.toList());
    }

    public WarehouseResponse getWarehouseById(UUID warehouseId) {
        var warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new WarehouseNotFoundException("No warehouse found with ID " + warehouseId));
        return warehouseMapper.toWarehouseResponse(warehouse);
    }
}
