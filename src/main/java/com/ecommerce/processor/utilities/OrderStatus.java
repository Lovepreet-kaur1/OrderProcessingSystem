package com.ecommerce.processor.utilities;

public enum OrderStatus {
    PENDING,      // Initial status - can be cancelled
    PROCESSING,   // Being prepared - auto-updated from PENDING
    SHIPPED,      // In transit
    DELIVERED,    // Reached customer
    CANCELLED     // Explicitly cancelled
}
