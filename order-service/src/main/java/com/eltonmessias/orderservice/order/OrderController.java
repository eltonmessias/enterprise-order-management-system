package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.orderItem.OrderItemRequest;
import com.eltonmessias.orderservice.orderItem.OrderItemResponse;
import com.eltonmessias.orderservice.orderItem.UpdateItemRequest;
import com.eltonmessias.orderservice.tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            Pageable pageable
            ) {
        return new ResponseEntity<>(orderService.getOrders(status, userId, dateFrom, dateTo, pageable), HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable("order-id")UUID orderId) {
        return new ResponseEntity<>(orderService.findOrderById(orderId), HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<?> deleteOrderById(
            @PathVariable("order-id")UUID orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{order-id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable("order-id") UUID orderId,
            @RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.updateOrder(orderId, request), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{order-id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("order-id") UUID orderId, Status status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId, status), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponse>> getOrderItems(@PathVariable("orderId") UUID orderId) {
        return new ResponseEntity<>(orderService.getOrderItems(orderId), HttpStatus.OK);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItemResponse> addOrderItem(@PathVariable("orderId") UUID orderId, @RequestBody OrderItemRequest request) {
        return new ResponseEntity<>(orderService.addOrderItem(orderId, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Map<String, Object>> removeItem(@PathVariable("orderId") UUID orderId, @PathVariable("itemId") UUID itemId) {
        BigDecimal newTotal = orderService.removeItemFromOrder(orderId, itemId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order item removed");
        response.put("orderId", orderId);
        response.put("itemId", itemId);
        response.put("newTotalAmount", newTotal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Map<String, Object>> updateItem(
            @PathVariable UUID orderId,
            @PathVariable UUID itemId,
            @RequestBody UpdateItemRequest request
            ) {
        BigDecimal newTotal = orderService.updateItemQuantity(orderId, itemId, request.quantity());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order item updated successfully");
        response.put("orderId", orderId);
        response.put("updatedItemId", itemId );
        response.put("newQuantity", request.quantity());
        response.put("newTotalAmount", newTotal);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
