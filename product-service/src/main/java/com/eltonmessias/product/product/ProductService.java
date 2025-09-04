package com.eltonmessias.product.product;

import com.eltonmessias.product.exception.ProductNotFoundException;
import com.eltonmessias.product.tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    public UUID createProduct(@Valid ProductRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        Product product = productMapper.toProduct(request, tenantId);
        return productRepository.save(product).getId();
    }


    public ProductResponse getProductById(UUID productId) {
        UUID tenantId = TenantContext.getTenantId();
        var product = productRepository.findByIdAndTenantId(productId, tenantId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(UUID productId, @Valid ProductRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        var product = productRepository.findByIdAndTenantId(productId, tenantId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productMapper.updateProduct(product, request, tenantId);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }
}
