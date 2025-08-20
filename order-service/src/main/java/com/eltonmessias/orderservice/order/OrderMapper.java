package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.Tenant.TenantResponse;
import com.eltonmessias.orderservice.orderItem.OrderItem;
import com.eltonmessias.orderservice.orderItem.OrderItemMapper;
import com.eltonmessias.orderservice.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {


    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public Order toOrder(OrderRequest orderRequest, TenantResponse itemsTotalPrice, UserResponse user) {
        Order order = new Order();



        if(orderRequest.orderItems() != null) {
            List<OrderItem> orderItems = orderRequest.orderItems().stream()
                    .map(itemReq -> OrderItem.builder()
                            .productId(itemReq.productId())
                            .order(order)
                            .quantity(itemReq.quantity())
                            .build())
                    .toList();
            order.setOrderItems(orderItems);
        }


        return order;
    }

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTenantId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getStatus(),
                order.getOrderType(),
                order.getCurrency(),
                order.getSubtotal(),
                order.getTaxAmount(),
                order.getShippingAmount(),
                order.getTotalAmount(),

                // Shipping Address
                order.getShippingFirstName(),
                order.getShippingLastName(),
                order.getShippingStreetLine1(),
                order.getShippingStreetLine2(),
                order.getShippingCity(),
                order.getShippingState(),
                order.getShippingPostalCode(),
                order.getShippingCountry(),

                // Billing Address
                order.getBillingFirstName(),
                order.getBillingLastName(),
                order.getBillingStreetLine1(),
                order.getBillingStreetLine2(),
                order.getBillingCity(),
                order.getBillingState(),
                order.getBillingPostalCode(),
                order.getBillingCountry(),

                order.getNotes(),
                order.getOrderedAt(),
                order.getConfirmedAt(),
                order.getShippedAt(),
                order.getDeliveredAt(),
                order.getCancelledAt(),
                order.getCreatedAt(),
                order.getUpdatedAt(),

                order.getOrderItems() != null
                    ? order.getOrderItems().stream()
                        .map(orderItemMapper::toOrderItemResponse).collect(Collectors.toList()) : List.of()

        );
    }
}
