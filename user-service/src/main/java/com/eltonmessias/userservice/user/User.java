package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.user.address.UserAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID tenantId;

    @Column(nullable = false, length = 50, unique = true)
    @Size(min = 5, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username should only contains, numbers, commas and undercores")
    private String username;

    @Column(nullable = false,length = 150)
    @Email(message = "E-mail should have the valid format")
    private String email;

    @Column(nullable = false,length = 150)
    @NotBlank(message = "The first name is mandatory")
    private String firstName;

    @Column(nullable = false,length = 150)
    @NotBlank(message = "The last name is mandatory")
    private String lastName;

    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    private String passwordHash;


    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.CUSTOMER;

    @Enumerated(EnumType.STRING)
    private STATUS status = STATUS.ACTIVE;

    private Boolean emailVerified = false;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private UserAddress address;



    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}


