package com.eltonmessias.orderservice.Product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "product-service"
//        url = "${application.config.product-url}"
)
public interface ProductClient {
    @GetMapping("/api/v1/product/{productId}")
    ProductResponse findById(@PathVariable UUID productId);
}
