package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.user.address.UserAddressRequest;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRequest(
        UUID tenantId,
        String username,
        String email,
        String firstName,
        String lastName,
        String passwordHash,
        ROLE role,
        STATUS status,
        Boolean emailVerified,
//        Address
        UserAddressRequest address
) {
}
