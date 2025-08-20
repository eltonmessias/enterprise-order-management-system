package com.eltonmessias.paymentservice.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID tenantId;
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentProvider paymentProvider;
    private String externalPaymentId;
    private BigDecimal amount;
    private String currency = "USD";
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    private LocalDateTime paymentDate;
    private LocalDateTime updateDate;


    @PrePersist
    public void onCreate() {
        this.paymentDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
