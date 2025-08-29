package com.eltonmessias.orderservice.orderItem;

import com.eltonmessias.orderservice.Inventory.InventoryResponse;
import com.eltonmessias.orderservice.Product.ProductResponse;
import com.eltonmessias.orderservice.order.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderItemMapper {

    public OrderItem toOrderItem(OrderItemRequest request, ProductResponse product, InventoryResponse inventory) {
        return OrderItem.builder()
                .order(
                        Order.builder().id(request.orderId()).build()
                )
                .productId(request.productId())
                .sku(product.sku())
                .productName(product.productName())
                .quantity(request.quantity())
                .unitPrice(product.unitPrice())
                .totalPrice(product.unitPrice().multiply(BigDecimal.valueOf(request.quantity())))
                .warehouseId(product.warehouseId())
                .status(Status.RESERVED)
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProductId(),
                orderItem.getSku(),
                orderItem.getProductName(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getTotalPrice(),
                orderItem.getWarehouseId()
//                orderItem.getStatus(),
//                orderItem.getCreatedAt(),
//                orderItem.getUpdatedAt()
        );
    }
}
