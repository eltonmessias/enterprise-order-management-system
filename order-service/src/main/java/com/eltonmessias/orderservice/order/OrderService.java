package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.Product.ProductClient;
import com.eltonmessias.orderservice.Tenant.TenantClient;
import com.eltonmessias.orderservice.Tenant.TenantResponse;
import com.eltonmessias.orderservice.exception.OrderNotFoundException;
import com.eltonmessias.orderservice.kafka.events.OrderCreatedEvent;
import com.eltonmessias.orderservice.kafka.producer.OrderEventProducer;
import com.eltonmessias.orderservice.orderItem.*;
import com.eltonmessias.orderservice.payment.PaymentClient;
import com.eltonmessias.orderservice.user.UserClient;
import com.eltonmessias.orderservice.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TenantClient tenantClient;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final OrderItemService orderItemService;
    private final OrderEventProducer orderEventProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public OrderResponse createOrder(@Valid OrderRequest request, UUID tenantID) {
        TenantResponse tenant = tenantClient.findById(tenantID);
        UserResponse user = userClient.findById(request.userId());
        Order order = orderMapper.toOrder(request, tenant, user);
        orderRepository.save(order);

        for (OrderItemRequest orderItemRequest: request.orderItems()) {

            orderItemService.createOrderItem(
                    new OrderItemRequest(
                            orderItemRequest.productId(),
                            order.getId(),
                            orderItemRequest.quantity()
                    )
            );
        }

        calculateTotals(order);


        String userFullName = user.firstName() + " " + user.lastName();
        orderEventProducer.publishOrderCreated(
                new OrderCreatedEvent(
                        order.getId(),
                        order.getOrderNumber(),
                        request.userId(),
                        userFullName,
                        user.email(),
                        order.getTotalAmount(),
                        order.getCreatedAt(),
                        order.getOrderItems().stream().map(orderItemMapper::toOrderItemResponse).toList()
                )
        );

        log.info("Order {} created and event published", order.getId());


        return orderMapper.toOrderResponse(order);

    }

    public void calculateTotals(Order order) {
        BigDecimal subtotal = order.getOrderItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setSubtotal(subtotal);

        BigDecimal tax = order.getTaxAmount() != null ? order.getTaxAmount() : BigDecimal.ZERO;
        BigDecimal shipping = order.getShippingAmount() != null ? order.getShippingAmount() : BigDecimal.ZERO;

        order.setTaxAmount(subtotal.add(tax).add(shipping));
        orderRepository.save(order);
    }


    public List<OrderResponse> findAllOrders(UUID tenantID) {
        List<Order> orders = orderRepository.findAllByTenantId(tenantID);
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse findOrderById(UUID orderId, UUID tenantID) {
        var order = orderRepository.findByIdAndTenantId(orderId, tenantID).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));
        return orderMapper.toOrderResponse(order);
    }

    public void deleteOrderById(UUID orderId, UUID tenantID) {
        var order = orderRepository.findByIdAndTenantId(orderId, tenantID).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));
        orderRepository.delete(order);
    }

    public OrderResponse updateOrder(UUID orderId, @Valid OrderRequest request, UUID tenantID) {
        var order = orderRepository.findByIdAndTenantId(orderId, tenantID).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));
        TenantResponse tenant = tenantClient.findById(request.tenantId());
        UserResponse user = userClient.findById(request.userId());
        order = orderMapper.toOrder(request, tenant, user);
        orderRepository.save(order);

        for (OrderItemRequest orderItemRequest: request.orderItems()) {

            orderItemService.createOrderItem(
                    new OrderItemRequest(
                            orderItemRequest.productId(),
                            order.getId(),
                            orderItemRequest.quantity()
                    )
            );
        }

        calculateTotals(order);
        return orderMapper.toOrderResponse(order);
    }


}
