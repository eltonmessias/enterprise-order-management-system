package com.eltonmessias.orderservice.Tenant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "tenant-service"
//        url = "${application.config.tenant-url}"
)
public interface TenantClient {

    @GetMapping("/api/v1/oms/tenant/{tenantId}")
    TenantResponse findById(@PathVariable UUID tenantId);
}
