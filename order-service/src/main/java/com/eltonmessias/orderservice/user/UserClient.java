package com.eltonmessias.orderservice.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "user-service",
        url = "${application.config.user-url}"
)
public interface UserClient {

    @GetMapping("/api/v1/user/{userId}")
    UserResponse findById(@PathVariable UUID userId);

}
