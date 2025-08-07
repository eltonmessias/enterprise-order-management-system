package com.eltonmessias.userservice.tenant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "tenant-service",
        url = "${application.config.tenant-url}"
)
public interface TenantClient{
    @GetMapping("/{tenant-id}")
    TenantResponse getTenantById(@PathVariable("tenant-id") UUID tenantId);
}
