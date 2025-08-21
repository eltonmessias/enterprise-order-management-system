package com.eltonmessias.paymentservice.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestBody @Valid PaymentRequest request
    ) {
        return new ResponseEntity<>(paymentService.createPayment(tenantId, request), HttpStatus.CREATED);
    }

    @PostMapping("/{paymentId}/confirm")
    public ResponseEntity<Void> confirmPayment(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable UUID paymentId, String externalPaymentId
    ) {
        paymentService.confirmPayment(UUID.fromString(tenantId), paymentId, externalPaymentId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments(@RequestHeader("X-Tenant-Id") String tenantId) {
        return new ResponseEntity<>(paymentService.findAllPaymentsByTenantId(UUID.fromString(tenantId)), HttpStatus.OK);
    }


}
