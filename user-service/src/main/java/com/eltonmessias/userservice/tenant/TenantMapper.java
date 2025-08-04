package com.eltonmessias.userservice.tenant;

import com.eltonmessias.userservice.user.User;
import com.eltonmessias.userservice.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantMapper {

    private final UserRepository userRepository;

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
                tenant.getSubscriptionPlan()
        );
    }
}
