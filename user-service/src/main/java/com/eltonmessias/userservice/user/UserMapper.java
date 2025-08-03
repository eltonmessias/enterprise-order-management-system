package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.tenant.Tenant;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getTenant().getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getEmailVerified(),
                user.getLastLoginAt(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getAddress()
        );
    }

    public User toUser(@Valid UserRequest request) {
        return User.builder()
                .username(request.username())
                .tenant(Tenant.builder().id(request.tenantId()).build())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .role(request.role())
                .status(request.status())
                .emailVerified(request.emailVerified())
                .lastLoginAt(request.lastLoginAt())
                .createdAt(request.createdAt())
                .updatedAt(request.updatedAt())
                .address(UserAddress.builder()
                        .city(request.city())
                        .state(request.state())
                        .country(request.country())
                        .street(request.street())
                        .build())
                .build();
    }
}
