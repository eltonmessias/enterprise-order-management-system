package com.eltonmessias.orderservice.Tenant;

import java.util.UUID;

public record TenantResponse(
        UUID tenantId,
        String name
) {
}
