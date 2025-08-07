package com.eltonmessias.inventoryservice.Tenant;

import java.util.UUID;

public record TenantResponse(
        UUID tenantId,
        String name
) {
}
