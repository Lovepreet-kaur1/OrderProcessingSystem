package com.ecommerce.processor.dto;

import java.util.List;

public class OrderCreationRepository {
    private Long customerId;
    private List<ItemRequest> items;

    // Inner class for item details
    public static class ItemRequest {
        private Long productId;
        private Integer quantity;

        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public List<ItemRequest> getItems() { return items; }
    public void setItems(List<ItemRequest> items) { this.items = items; }
}