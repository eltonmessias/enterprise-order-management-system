package com.eltonmessias.inventoryservice.warehouse;

import com.eltonmessias.inventoryservice.Tenant.TenantResponse;
import org.springframework.stereotype.Service;

@Service
public class WarehouseMapper {


    public Warehouse toWarehouse(WarehouseRequest request, TenantResponse tenant) {
        return Warehouse.builder()
                .tenantId(tenant.tenantId())
                .code(request.code())
                .name(request.name())
                .address_line1(request.address_line1())
                .address_line2(request.address_line2())
                .city(request.city())
                .state(request.state())
                .postalCode(request.postalCode())
                .country(request.country())
                .isActive(request.isActive())
                .build();
    }

    public WarehouseResponse toWarehouseResponse(Warehouse warehouse) {
        return new WarehouseResponse(
                warehouse.getId(),
                warehouse.getTenantId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getAddress_line1(),
                warehouse.getAddress_line2(),
                warehouse.getCity(),
                warehouse.getState(),
                warehouse.getPostalCode(),
                warehouse.getCountry(),
                warehouse.getIsActive(),
                warehouse.getCreatedAt(),
                warehouse.getUpdatedAt(),
                warehouse.getInventories()
        );
    }


}
