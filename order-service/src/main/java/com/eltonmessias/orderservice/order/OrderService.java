package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.tenant.TenantContext;
import com.eltonmessias.orderservice.exception.OrderNotFoundException;
import com.eltonmessias.orderservice.kafka.events.OrderCreatedEvent;
import com.eltonmessias.orderservice.kafka.producer.OrderEventProducer;
import com.eltonmessias.orderservice.orderItem.*;
import com.eltonmessias.orderservice.user.UserClient;
import com.eltonmessias.orderservice.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final UserClient userClient;
    private final OrderItemService orderItemService;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public OrderResponse createOrder(@Valid OrderRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        UserResponse user = userClient.findById(request.userId());
        Order order = orderMapper.toOrder(request, tenantId, user);
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


    public Page<OrderResponse> getOrders(Status status, UUID userId, LocalDate dateFrom, LocalDate dateTo,
                                         Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        LocalDateTime from = (dateFrom != null) ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = (dateTo != null) ? dateTo.atTime(23, 59, 59) : null;
        return orderRepository.findAll(
                OrderSpecification.withFilters(tenantId, status, userId, from, to), pageable
        ).map(orderMapper::toOrderResponse);
    }

    public OrderResponse findOrderById(UUID orderId) {
        UUID tenantId = TenantContext.getTenantId();
        var order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));
        return orderMapper.toOrderResponse(order);
    }

    public void deleteOrderById(UUID orderId) {
        UUID tenantId = TenantContext.getTenantId();
        var order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));
        orderRepository.delete(order);
    }

    public OrderResponse updateOrder(UUID orderId, @Valid OrderRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        var order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));

        UserResponse user = userClient.findById(request.userId());
        order = orderMapper.toOrder(request, tenantId, user);
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
