package com.ecommerce.processor.service;


import com.ecommerce.processor.utilities.OrderStatus;
import com.ecommerce.processor.dto.OrderCreationRepository;
import com.ecommerce.processor.exception.OrderNotFoundException;
import com.ecommerce.processor.model.Order;
import com.ecommerce.processor.model.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {
    // In-memory "database" simulation
    private final ConcurrentHashMap<Long, Order> orderStore = new ConcurrentHashMap<>();
    private final AtomicLong orderIdGenerator = new AtomicLong(0);

    // Placeholder data for product prices (simulates ProductService lookup)
    private final ConcurrentHashMap<Long, BigDecimal> productPrices = new ConcurrentHashMap<>() {{
        put(101L, new BigDecimal("10.00"));
        put(102L, new BigDecimal("25.50"));
        put(103L, new BigDecimal("5.00"));
    }};

    /**
     * Create an order
     */
    public Order createOrder(OrderCreationRepository request) {
        Order order = new Order();
        order.setId(orderIdGenerator.incrementAndGet());
        order.setCustomerId(request.getCustomerId());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = request.getItems().stream()
            .map(itemReq -> {
                // Simulate looking up product price and calculating item total
                BigDecimal price = productPrices.getOrDefault(itemReq.getProductId(), BigDecimal.ONE);
                totalAmount.add(price.multiply(new BigDecimal(itemReq.getQuantity())));

                return new OrderItem(
                    itemReq.getProductId(),
                    itemReq.getQuantity(),
                    price // Price at the time of order
                );
            })
            .collect(Collectors.toList());

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING); // Set initial status

        orderStore.put(order.getId(), order);
        return order;
    }

    /**
     * Retrieve order details
     */
    public Order getOrderDetails(Long orderId) {
        Order order = orderStore.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        return order;
    }

    /**
     * List all orders
     */
    public List<Order> listOrders(OrderStatus status) {
        if (status == null) {
            return orderStore.values().stream().toList();
        }
        // Filter by status if provided
        return orderStore.values().stream()
            .filter(order -> order.getStatus() == status)
            .toList();
    }

    /**
     * Cancel an order (ONLY if PENDING)
     */
    public Order cancelOrder(Long orderId) {
        Order order = getOrderDetails(orderId);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Order ID " + orderId + " cannot be cancelled. Status is " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        // In a real system, you would add logic here to restore product stock.
        orderStore.put(order.getId(), order);
        return order;
    }

    /**
     * Used by the Scheduler (Background Job)
     */
    public List<Order> findPendingOrders() {
        return listOrders(OrderStatus.PENDING);
    }

    /**
     * Used by the Scheduler and Controller (Update Status)
     */
    public Order saveOrder(Order order) {
        // Simple put operation acts as save/update
        orderStore.put(order.getId(), order);
        return order;
    }

    /**
     * Optional: For manual status updates (like SHIPPED/DELIVERED)
     */
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderDetails(orderId);
        order.setStatus(newStatus);
        return saveOrder(order);
    }
}