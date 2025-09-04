package com.eltonmessias.orderservice.order;

import com.eltonmessias.orderservice.orderItem.OrderItem;
import com.eltonmessias.orderservice.user.UserResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID tenantId;
    @Column(unique = true, nullable = false)
    private String orderNumber;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    @Enumerated(EnumType.STRING)
    private OrderType orderType = OrderType.STANDARD;
    private String currency = "USD";
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

//    Shipping Address
    private String shippingFirstName;
    private String shippingLastName;
    private String shippingStreetLine1;
    private String shippingStreetLine2;
    private String shippingCity;
    private String shippingState;
    private String shippingPostalCode;
    private String shippingCountry;

//    Billing Address
    private String billingFirstName;
    private String billingLastName;
    private String billingStreetLine1;
    private String billingStreetLine2;
    private String billingCity;
    private String billingState;
    private String billingPostalCode;
    private String billingCountry;


    @Lob
    private String notes;
    private LocalDateTime orderedAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PostPersist
    protected void onPostPersist() {
        String year = String.valueOf(Year.now().getValue());
        this.orderNumber = String.format("ORD-%s-%s", year, this.id.toString().substring(0, 8).toUpperCase());
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        ensureEditable();
        orderItems.add(item);
        item.setOrder(this);
        recalculateTotal();
    }

    public void removeItem(OrderItem item) {
        ensureEditable();
        orderItems.remove(item);
        recalculateTotal();
    }

    public void recalculateTotal() {
        this.subtotal = orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = subtotal.add(
                (taxAmount != null ? taxAmount : BigDecimal.ZERO)
                        .add(shippingAmount != null ? shippingAmount : BigDecimal.ZERO)
        );
    }

    public void ensureEditable() {
        if (status != Status.PENDING) {
            throw new IllegalArgumentException("Cannot update status of an order that is " + status);
        }
    }

    public void updateFromRequest(OrderRequest request, UUID tenantId, UserResponse user) {
        ensureEditable();
        this.userId = request.userId();
        this.tenantId = tenantId;
        this.updatedAt = LocalDateTime.now();
        this.recalculateTotal();
    }

}
