package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.kafka.events.OrderStatusUpdatedEvent;
import com.eltonmessias.orderservice.tenant.TenantContext;
import com.eltonmessias.orderservice.exception.OrderNotFoundException;
import com.eltonmessias.orderservice.kafka.events.OrderCreatedEvent;
import com.eltonmessias.orderservice.kafka.producer.OrderEventProducer;
import com.eltonmessias.orderservice.orderItem.*;
import com.eltonmessias.orderservice.user.UserClient;
import com.eltonmessias.orderservice.user.UserResponse;
import jakarta.persistence.EntityNotFoundException;
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
    private final OrderItemRepository orderItemRepository;


    @Transactional
    public OrderResponse createOrder(@Valid OrderRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        UserResponse user = userClient.findById(request.userId());
        Order order = orderMapper.toOrder(request, tenantId, user);

        for (OrderItemRequest itemReq: request.orderItems()) {
            OrderItem item = orderItemService.createOrderItem(itemReq);
            order.addItem(item);

        }
        orderRepository.save(order);


        orderEventProducer.publishOrderCreated(
                new OrderCreatedEvent(
                        order.getId(),
                        order.getOrderNumber(),
                        request.userId(),
                        user.firstName() + " " + user.lastName(),
                        user.email(),
                        order.getTotalAmount(),
                        order.getCreatedAt(),
                        order.getOrderItems().stream().map(orderItemMapper::toOrderItemResponse).toList()
                )
        );

        log.info("Order {} created and event published", order.getId());
        return orderMapper.toOrderResponse(order);
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

    @Transactional
    public void deleteOrderById(UUID orderId) {
        UUID tenantId = TenantContext.getTenantId();
        var order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));
        orderRepository.delete(order);
    }

    @Transactional
    public OrderResponse updateOrder(UUID orderId, @Valid OrderRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        var order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException(
                "Order not found"));

        UserResponse user = userClient.findById(request.userId());

        order.updateFromRequest(request, tenantId, user);

        for (OrderItemRequest itemReq: request.orderItems()) {
            OrderItem item = orderItemService.createOrderItem(itemReq);
            order.addItem(item);
        }

        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }


    @Transactional
    public String updateOrderStatus(UUID orderId, Status status) {
        UUID tenantId = TenantContext.getTenantId();
        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);

        UserResponse user = userClient.findById(order.getUserId());

        orderEventProducer.publishOrderStatusUpdated(
                new OrderStatusUpdatedEvent(
                        order.getId(),
                        order.getStatus(),
                        order.getOrderNumber(),
                        order.getUserId(),
                        user.firstName() + " " + user.lastName(),
                        user.email(),
                        order.getTotalAmount(),
                        order.getCreatedAt(),
                        order.getOrderItems().stream().map(orderItemMapper::toOrderItemResponse).toList()
                )

        );
        return "Order status was successfully updated to: " + order.getStatus();
    }

    public List<OrderItemResponse> getOrderItems(UUID orderId) {
        UUID tenantId = TenantContext.getTenantId();
        var order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException(
                "Order " +
                "not found"));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order.getId());
        return orderItems.stream().map(orderItemMapper::toOrderItemResponse).collect(Collectors.toList());
    }


    @Transactional
    public OrderItemResponse addOrderItem(UUID orderId, OrderItemRequest request) {
        UUID tenantId = TenantContext.getTenantId();
        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        OrderItem item = orderItemService.createOrderItem(request);
        order.addItem(item);

        orderRepository.save(order);
        return orderItemMapper.toOrderItemResponse(item);
    }

    @Transactional
    public void removeItemFromOrder(UUID orderId, UUID itemId) {
        UUID tenantId = TenantContext.getTenantId();
        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if(order.getStatus() != Status.PENDING) {
            throw new IllegalArgumentException("Cannot remove item from an order that is " + order.getStatus());
        }
        OrderItem item =
                order.getOrderItems().stream()
                        .filter(i -> i.getId().equals(itemId)).findFirst().orElseThrow(() -> new EntityNotFoundException("Item not found"));
        order.getOrderItems().remove(item);

        orderRepository.save(order);
    }

    @Transactional
    public BigDecimal updateItemQuantity(UUID orderId, UUID itemId, int quantity) {
        UUID tenantId = TenantContext.getTenantId();

        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId).orElseThrow(() -> new OrderNotFoundException("Order not found"));


        OrderItem item = order.getOrderItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.updateQuantity(quantity);
        order.recalculateTotal();

        orderRepository.save(order);
        return order.getTotalAmount();
    }
}
