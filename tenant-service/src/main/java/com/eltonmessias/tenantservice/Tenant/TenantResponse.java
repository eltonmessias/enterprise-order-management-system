package com.eltonmessias.tenantservice.Tenant;

import java.time.LocalDateTime;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        String subscriptionPlan,
        Integer maxUsers
) {
}
