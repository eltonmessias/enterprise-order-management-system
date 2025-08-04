package com.eltonmessias.userservice.user.address;

public record UserAddressRequest(
        String street,
        String city,
        String state,
        String country
) {
}
