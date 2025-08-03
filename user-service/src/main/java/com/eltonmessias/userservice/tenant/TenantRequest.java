package com.eltonmessias.userservice.tenant;

import java.util.List;
import java.util.UUID;

public record TenantRequest(
        UUID id,
        String name,
        String SubscriptionPlan,
        List<UUID> userIds

) {
}
