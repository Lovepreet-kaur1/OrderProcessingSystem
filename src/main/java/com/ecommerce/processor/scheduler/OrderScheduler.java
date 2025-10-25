package com.ecommerce.processor.scheduler;


import com.ecommerce.processor.utilities.OrderStatus;
import com.ecommerce.processor.model.Order;
import com.ecommerce.processor.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderScheduler {

    private final OrderService orderService;

    public OrderScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Requirement 3: Automatically update PENDING orders to PROCESSING every 5 minutes.
     * fixedRate = 300000 milliseconds (5 minutes)
     */
    @Scheduled(fixedRate = 300000)
    public void processPendingOrders() {
        System.out.println("--- Scheduler running: Checking for PENDING orders...");

        List<Order> pendingOrders = orderService.findPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println("--- No PENDING orders to process.");
            return;
        }

        pendingOrders.forEach(order -> {
            order.setStatus(OrderStatus.PROCESSING);
            orderService.saveOrder(order);
            System.out.println("Order ID " + order.getId() + " updated to PROCESSING.");
        });

        System.out.println("--- " + pendingOrders.size() + " orders successfully moved to PROCESSING.");
    }
}