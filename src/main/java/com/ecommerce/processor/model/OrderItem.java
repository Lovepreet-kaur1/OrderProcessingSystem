package com.ecommerce.processor.model;

import java.math.BigDecimal;

public class OrderItem {
    private Long productId;
    private Integer quantity;
    private BigDecimal priceAtTimeOfOrder; // Store price to ensure accuracy

    // Constructor, Getters, and Setters...
    public OrderItem(Long productId, Integer quantity, BigDecimal priceAtTimeOfOrder) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
    }

    // Getters and Setters (omitted for brevity)
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPriceAtTimeOfOrder() { return priceAtTimeOfOrder; }
}
