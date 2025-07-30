package com.eltonmessias.tenantservice.Tenant;

import com.eltonmessias.tenantservice.exception.TenantNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper mapper;

    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream().map(mapper::toTenantResponse).collect(Collectors.toList());
    }

    public TenantResponse createTenant(@Valid TenantRequest request) {
        var newTenant = mapper.toTenant(request);
        return mapper.toTenantResponse(tenantRepository.save(newTenant));
    }

    public TenantResponse findTenantById(UUID tenantId) {
        var tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
        return mapper.toTenantResponse(tenant);
    }
}
