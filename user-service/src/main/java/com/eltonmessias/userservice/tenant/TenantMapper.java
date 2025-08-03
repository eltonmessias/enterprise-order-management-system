package com.eltonmessias.userservice.tenant;

import com.eltonmessias.userservice.user.User;
import com.eltonmessias.userservice.user.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantMapper {

    private final UserRepository userRepository;

    public Tenant toTenant(@Valid TenantRequest request) {
        List<User> users = userRepository.findAllById(request.userIds());
        return Tenant.builder()
                .id(request.id())
                .name(request.name())
                .subscriptionPlan(request.SubscriptionPlan())
                .users(users)
                .build();
    }

    public TenantResponse toTenantResponse(Tenant tenant) {
        List<String> userNames = tenant.getUsers().stream().map(User::getFirstName).toList();
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getCreatedAt(),
                tenant.getSubscriptionPlan(),
                userNames
        );
    }
}
