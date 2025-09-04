package com.eltonmessias.tenantservice.tenant;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TenantRequest(
        @NotNull(message = "The name is mandatory")
        String name,
        @NotNull(message = "The email is mandatory")
        @Email
        String email,
        @NotNull(message = "The address is mandatory")
        String address,
        SubscriptionPlan subscriptionPlan
) {
}
