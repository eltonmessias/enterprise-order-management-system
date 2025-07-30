package com.eltonmessias.tenantservice.Tenant;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TenantMapper {

    public Tenant toTenant(@Valid TenantRequest request) {
        return Tenant.builder()
                .id(request.id())
                .name(request.name())
                .subscriptionPlan(request.SubscriptionPlan())
                .build();
    }

    public TenantResponse toTenantResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getCreatedAt(),
                tenant.getSubscriptionPlan(),
                tenant.getMaxUsers()
        );
    }
}
