package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.Product.ProductClient;
import com.eltonmessias.orderservice.Tenant.TenantClient;
import com.eltonmessias.orderservice.Tenant.TenantResponse;
import com.eltonmessias.orderservice.orderItem.*;
import com.eltonmessias.orderservice.user.UserClient;
import com.eltonmessias.orderservice.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
