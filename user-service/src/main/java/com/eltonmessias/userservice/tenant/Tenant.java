package com.eltonmessias.userservice.tenant;

import com.eltonmessias.userservice.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    

    @OneToMany(mappedBy = "tenant")
    private List<User> users;

    @ElementCollection
    @CollectionTable(name = "tenant_configs")
    private Map<String, String> tenantConfigs;

}
