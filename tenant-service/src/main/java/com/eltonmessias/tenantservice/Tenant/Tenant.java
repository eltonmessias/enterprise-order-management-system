package com.eltonmessias.tenantservice.Tenant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "subscription_plan")
    private String subscriptionPlan;

    @Column(name = "max_users")
    private int maxUsers;

    @ElementCollection
    @CollectionTable(name = "tenant_configs")
    private Map<String, String> tenantConfigs;

}
