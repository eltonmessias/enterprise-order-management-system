package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.Product.ProductClient;
import com.eltonmessias.orderservice.Tenant.TenantClient;
import com.eltonmessias.orderservice.Tenant.TenantResponse;
import com.eltonmessias.orderservice.kafka.events.OrderCreatedEvent;
import com.eltonmessias.orderservice.kafka.producer.OrderEventProducer;
import com.eltonmessias.orderservice.orderItem.*;
import com.eltonmessias.orderservice.payment.PaymentClient;
import com.eltonmessias.orderservice.payment.PaymentRequest;
import com.eltonmessias.orderservice.user.UserClient;
import com.eltonmessias.orderservice.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final TenantClient tenantClient;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final OrderItemService orderItemService;
    private final OrderEventProducer orderEventProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public OrderResponse createOrder(@Valid OrderRequest request) {
        TenantResponse tenant = tenantClient.findById(request.tenantId());
        UserResponse user = userClient.findById(request.userId());
//        List<ProductResponse> products = productClient.findAllById(request  )
        Order order = orderMapper.toOrder(request, tenant, user);
        orderRepository.save(order);

        for (OrderItem orderItemRequest: request.orderItems()) {

            orderItemService.createOrderItem(
                    new OrderItemRequest(
                            orderItemRequest.getProductId(),
                            order.getId(),
                            orderItemRequest.getQuantity()
                    )
            );
        }

        calculateTotals(order);

//        var paymentRequest = new PaymentRequest(
//                order.getTotalAmount(),
//                order.getId(),
//                order.getOrder_number(),
//                user
//        );


        orderEventProducer.publishOrderCreated(
                new OrderCreatedEvent(
                        order.getOrder_number(),
                        request.userId(),
                        order.getTotalAmount(),
                        request.orderItems(),
                        order.getCreatedAt()
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
    }
}
