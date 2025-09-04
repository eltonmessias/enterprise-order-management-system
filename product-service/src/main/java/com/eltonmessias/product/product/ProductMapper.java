package com.eltonmessias.product.product;

import com.eltonmessias.product.category.Category;
import com.eltonmessias.product.category.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductMapper {


    private final CategoryRepository categoryRepository;



    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTenantId(),
                product.getSku(),
                product.getName(),
                product.getName(),
                product.getCategory().getName(),
                product.getBrand(),
                product.getUnitPrice(),
                product.getCostPrice(),
                product.getWeight(),
                product.getDimensionsLength(),
                product.getDimensionsWidth(),
                product.getDimensionsHeight(),
                product.getStatus(),
                product.getIsSerialized(),
                product.getRequiresShipping(),
                product.getCreatedAt(),
                product.getUpdatedAt()
                );
    }

    public Product toProduct(ProductRequest request, UUID tenantId) {
        var category = categoryRepository.existsById(request.categoryId());
        if (!category) { throw new RuntimeException("Category not found"); }
        return Product.builder()
                .tenantId(tenantId)
                .sku(request.sku())
                .name(request.name())
                .description(request.description())
                .category(Category.builder().id(request.categoryId()).build())
                .brand(request.brand())
                .unitPrice(request.unitPrice())
                .costPrice(request.costPrice())
                .weight(request.weight())
                .dimensionsLength(request.dimensionsLength())
                .dimensionsWidth(request.dimensionsWidth())
                .dimensionsHeight(request.dimensionsHeight())
                .status(request.status())
                .isSerialized(request.isSerialized())
                .requiresShipping(request.requiresShipping())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public void updateProduct(Product product, @Valid ProductRequest request, UUID tenantId) {
        product.setTenantId(tenantId);
        product.setSku(request.sku());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setCategory(Category.builder().id(request.categoryId()).build());
        product.setBrand(request.brand());
        product.setUnitPrice(request.unitPrice());
        product.setCostPrice(request.costPrice());
        product.setWeight(request.weight());
        product.setDimensionsLength(request.dimensionsLength());
        product.setDimensionsWidth(request.dimensionsWidth());
        product.setDimensionsHeight(request.dimensionsHeight());
        product.setStatus(request.status());
        product.setRequiresShipping(request.requiresShipping());
    }
}
