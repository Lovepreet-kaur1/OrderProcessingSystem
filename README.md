# OrderProcessingSystem
E-commerce Order Processing System. The system should allow customers to place orders, track their status, and support basic order operations.
Requirements - 
Core Features
  1) Create an order: Customers should be able to place an order with multiple items.
  2) Retrieve order details: The system should allow fetching order details by order ID.
  3) Update order status: The order should have statuses like PENDING, PROCESSING, SHIPPED, and DELIVERED. A background job should automatically update PENDING orders to PROCESSING every 5 minutes.
  4) List all orders: Retrieve all orders, optionally filtered by status.
  5) Cancel an order: Customers should be able to cancel an order, but only if it’s still in PENDING status.
