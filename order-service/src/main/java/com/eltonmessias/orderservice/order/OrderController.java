package com.eltonmessias.orderservice.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-Tenant-Id") UUID tenantID,
            @RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request, tenantID), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestHeader("X-Tenant-Id") UUID tenantID) {
        return new ResponseEntity<>(orderService.findAllOrders(tenantID), HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @RequestHeader("X-Tenant-Id") UUID tenantID,
            @PathVariable("order-id")UUID orderId) {
        return new ResponseEntity<>(orderService.findOrderById(orderId, tenantID), HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<?> deleteOrderById(
            @RequestHeader("X-Tenant-Id") UUID tenantID,
            @PathVariable("order-id")UUID orderId) {
        orderService.deleteOrderById(orderId, tenantID);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{order-id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @RequestHeader("X-Tenant-Id") UUID tenantID,
            @PathVariable("order-id") UUID orderId,
            @RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.updateOrder(orderId, request, tenantID), HttpStatus.ACCEPTED);
    }
}
