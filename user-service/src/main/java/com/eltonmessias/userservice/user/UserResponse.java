package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.tenant.Tenant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
        UserAddress address
) {
}
