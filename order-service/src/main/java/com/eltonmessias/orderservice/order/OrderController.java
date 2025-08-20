package com.eltonmessias.orderservice.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpOutputMessage;
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
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        if (orderService.findAllOrders().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderService.findAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("order-id")UUID orderId) {
        return new ResponseEntity<>(orderService.findOrderById(orderId), HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("order-id")UUID orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{order-id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable("order-id") UUID orderId, @RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(orderService.updateOrder(orderId, request), HttpStatus.ACCEPTED);
    }
}
