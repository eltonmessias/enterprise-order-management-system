package com.eltonmessias.orderservice.order;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderSpecification {

    public static Specification<Order> withFilters(
            UUID tenantId,
            Status status,
            UUID userId,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    ) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            // Always filters by Tenant
            predicates.getExpressions().add(cb.equal(root.get("tenantId"), tenantId));

            if (status != null) {
                predicates.getExpressions().add(cb.equal(root.get("status"), status));
            }

            if (userId != null) {
                predicates.getExpressions().add(cb.equal(root.get("userId"), userId));
            }

            if (dateFrom != null) {
                predicates.getExpressions().add(cb.greaterThanOrEqualTo(root.get("dateFrom"), dateFrom));
            }

            if (dateTo != null) {
                predicates.getExpressions().add(cb.lessThanOrEqualTo(root.get("dateTo"), dateTo));
            }

            return predicates;
        };
    }

}
