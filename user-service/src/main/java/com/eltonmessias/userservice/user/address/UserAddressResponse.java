package com.eltonmessias.userservice.user.address;

public record UserAddressResponse(
        String street,
        String city,
        String state,
        String country
) {
}
