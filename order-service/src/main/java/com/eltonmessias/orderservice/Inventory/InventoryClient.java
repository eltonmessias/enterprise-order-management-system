package com.eltonmessias.orderservice.Inventory;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "inventory-service"
//        url = "${application.config.inventory-url}"
)
public interface InventoryClient {

    @GetMapping("/inventory/{productId}")
    InventoryResponse findById(@PathVariable UUID productId);

    @PutMapping("/inventory/{productId}/quantity")
    void updateQuantity(@PathVariable UUID productId, @RequestParam int quantityChange);
}
