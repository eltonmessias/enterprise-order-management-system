package com.eltonmessias.tenantservice.DTOs;

import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        String subscriptionPlan,
        Integer maxUsers
) {
}
