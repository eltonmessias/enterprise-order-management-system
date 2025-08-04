package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.user.address.UserAddress;
import com.eltonmessias.userservice.user.address.UserAddressResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
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
        UserAddressResponse address
) {
}
