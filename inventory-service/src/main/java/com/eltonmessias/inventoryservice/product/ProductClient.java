package com.eltonmessias.inventoryservice.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(
        name = "product-service"
//        url = "${application.config.product-url}"
)
public interface ProductClient {

    @GetMapping("/{product-id}")
    ProductResponse getProductById(@PathVariable("product-id") UUID productId);
}
