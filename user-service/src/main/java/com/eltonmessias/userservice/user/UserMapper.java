package com.eltonmessias.userservice.user;

import com.eltonmessias.userservice.tenant.Tenant;
import com.eltonmessias.userservice.tenant.TenantRepository;
import com.eltonmessias.userservice.user.address.UserAddress;
import com.eltonmessias.userservice.user.address.UserAddressResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final TenantRepository tenantRepository;


    public UserResponse toUserResponse(User user) {
        UserAddressResponse address = new UserAddressResponse(
                user.getAddress().getStreet(),
                user.getAddress().getCity(),
                user.getAddress().getState(),
                user.getAddress().getCountry()
        );
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
                address
        );
    }

    public User toUser(@Valid UserRequest request) {

        UserAddress address = UserAddress.builder()
                .street(request.address().street())
                .city(request.address().city())
                .state(request.address().state())
                .country(request.address().country())
                .build();



        User user = User.builder()
                .username(request.username())
                .tenant(Tenant.builder().id(request.tenantId()).build())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .passwordHash(request.passwordHash())
                .email(request.email())
                .role(request.role())
                .status(request.status())
                .emailVerified(request.emailVerified())
                .address(address)
                .build();

        address.setUser(user);
        return user;
    }
}
