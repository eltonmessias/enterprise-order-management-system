package com.eltonmessias.userservice.user;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRequest(
        UUID tenantId,
        String username,
        String email,
        String firstName,
        String lastName,
        ROLE role,
        STATUS status,
        Boolean emailVerified,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
//        Address
        String country,
        String state,
        String city,
        String postalCode,
        String street
) {
}
