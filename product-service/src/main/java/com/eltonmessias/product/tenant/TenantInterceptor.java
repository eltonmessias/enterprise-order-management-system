package com.eltonmessias.product.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class TenantInterceptor implements HandlerInterceptor {
    private static final String TENANT_HEADER = "X-Tenant-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantHeader = request.getHeader(TENANT_HEADER);
        if (tenantHeader == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing X-Tenant-Id");
            return false;
        }
        try {
            UUID tenantId = UUID.fromString(tenantHeader);
            TenantContext.setTenantId(tenantId);
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid X-Tenant-Id");
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContext.clear();
    }

}
