package com.eltonmessias.userservice.tenant;

import com.eltonmessias.userservice.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        String subscriptionPlan,
        List<String> users
) {
}
