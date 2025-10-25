package com.ecommerce.processor.controller;


import com.ecommerce.processor.utilities.OrderStatus;
import com.ecommerce.processor.dto.OrderCreationRepository;
import com.ecommerce.processor.model.Order;
import com.ecommerce.processor.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. Create an order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreationRepository request) {
        Order newOrder = orderService.createOrder(request);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    // 2. Retrieve order details
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(order);
    }

    // 4. List all orders (with optional status filter)
    @GetMapping
    public ResponseEntity<List<Order>> listOrders(
            @RequestParam(required = false) OrderStatus status) {
        List<Order> orders = orderService.listOrders(status);
        return ResponseEntity.ok(orders);
    }

    // 5. Cancel an order (must be PENDING)
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        Order cancelledOrder = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(cancelledOrder);
    }

    // Optional: Manual status update (for SHIPPED/DELIVERED)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}