package com.eltonmessias.tenantservice.DTOs;

import java.util.Map;

public record TentantRequest(
        String name,
        String SubscriptionPlan,
        Integer maxUsers
) {
}
