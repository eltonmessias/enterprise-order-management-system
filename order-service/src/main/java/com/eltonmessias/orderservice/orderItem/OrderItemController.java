package com.eltonmessias.orderservice.orderItem;

import com.eltonmessias.orderservice.order.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orderItems")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;


    @PostMapping
    public ResponseEntity<UUID> createOrderItem(@RequestBody OrderRequest request) {
        return orderItemService.createOrderItem(request);
    }
}
