package com.eltonmessias.tenantservice.tenant;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantMapper {


    public Tenant toTenant(@Valid TenantRequest request) {
        return Tenant.builder()
                .name(request.name())
                .email(request.email())
                .address(request.address())
                .subscriptionPlan(request.subscriptionPlan())
                .build();
    }

    public TenantResponse toTenantResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getTenantCode(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getAddress(),
                tenant.getSubscriptionPlan(),
                tenant.getCreatedAt(),
                tenant.getUpdatedAt()

        );
    }
}
