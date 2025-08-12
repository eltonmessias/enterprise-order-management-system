package com.eltonmessias.product.product;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping
    public ResponseEntity<UUID> createProduct(@RequestBody @Valid ProductRequest request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("product-id") UUID productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("product-id") UUID productId, @RequestBody @Valid ProductRequest request) {
        return new ResponseEntity<>(productService.updateProduct(productId, request), HttpStatus.ACCEPTED);
    }
}
