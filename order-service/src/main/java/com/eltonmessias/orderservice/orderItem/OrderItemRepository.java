package com.eltonmessias.orderservice.orderItem;

import com.eltonmessias.orderservice.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {


    List<OrderItem> order(Order order);

    List<OrderItem> findAllByOrder(UUID id);
}
