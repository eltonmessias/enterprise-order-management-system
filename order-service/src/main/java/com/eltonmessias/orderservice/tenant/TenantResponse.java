package com.eltonmessias.orderservice.tenant;

import java.util.UUID;

public record TenantResponse(
        UUID tenantId,
        String name
) {
}
