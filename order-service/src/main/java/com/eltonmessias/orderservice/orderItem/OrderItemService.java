package com.eltonmessias.orderservice.orderItem;

import com.eltonmessias.orderservice.Inventory.InventoryClient;
import com.eltonmessias.orderservice.Inventory.InventoryResponse;
import com.eltonmessias.orderservice.Product.ProductClient;
import com.eltonmessias.orderservice.Product.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    @Transactional
    public UUID createOrderItem(OrderItemRequest request) {
        ProductResponse product = productClient.findById(request.productId());
        InventoryResponse inventory = inventoryClient.findById(product.productId());
        if(inventory.quantityAvailable() < request.quantity()) {
            throw new RuntimeException("Not enough product quantity available");
        }

        var orderItem = orderItemMapper.toOrderItem(request, product, inventory);
        inventoryClient.updateQuantity(product.productId(), -request.quantity());


        return orderItemRepository.save(orderItem).getId();

    }
}
