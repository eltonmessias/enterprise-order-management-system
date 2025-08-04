package com.eltonmessias.userservice.tenant;

import com.eltonmessias.userservice.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        String subscriptionPlan
) {
}
