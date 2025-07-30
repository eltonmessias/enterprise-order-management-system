package com.eltonmessias.tenantservice.Tenant;

import java.util.UUID;

public record TenantRequest(
        UUID id,
        String name,
        String SubscriptionPlan,
        Integer maxUsers
) {
}
