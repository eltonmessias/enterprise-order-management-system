package com.eltonmessias.tenantservice.Tenant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/oms/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService service;

    @GetMapping
    public ResponseEntity<List<TenantResponse>> findAll() {
        return new ResponseEntity<>(service.getAllTenants(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@RequestBody @Valid TenantRequest request) {
        return ResponseEntity.ok(service.createTenant(request));
    }

    @GetMapping("/{tenant-id}")
    public ResponseEntity<TenantResponse> findById(@PathVariable("tenant-id") UUID tenantId) {
        return ResponseEntity.ok(service.findTenantById(tenantId));
    }
}
