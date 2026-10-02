package com.group4.th.year.project.smart.campus.Management.Service;

import com.group4.th.year.project.smart.campus.Management.CanteenOrder;
import com.group4.th.year.project.smart.campus.Management.MenuItem;
import com.group4.th.year.project.smart.campus.Management.OrderItem;
import com.group4.th.year.project.smart.campus.Management.Repo.CanteenOrderRepo;
import com.group4.th.year.project.smart.campus.Management.Repo.MenuItemRepo;
import com.group4.th.year.project.smart.campus.Management.Repo.OrderItemRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CanteenService {
    private final CanteenOrderRepo orderRepo;
    private final MenuItemRepo menuItemRepo;
    private final OrderItemRepo orderItemRepo;

    public CanteenService(CanteenOrderRepo orderRepo, MenuItemRepo menuItemRepo, OrderItemRepo orderItemRepo) {
        this.orderRepo = orderRepo;
        this.menuItemRepo = menuItemRepo;
        this.orderItemRepo = orderItemRepo;
    }

    /**
     * Simple request object for order items to be used in the API.
     */
    public static class OrderItemRequest {
        public Long menuItemId;
        public Integer quantity;

        public OrderItemRequest() {}
        public OrderItemRequest(Long menuItemId, Integer quantity) {
            this.menuItemId = menuItemId;
            this.quantity = quantity;
        }
    }

    @Transactional
    public CanteenOrder createOrder(CanteenOrder order, List<OrderItemRequest> itemRequests) {
        if (itemRequests == null || itemRequests.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest req : itemRequests) {
            MenuItem item = menuItemRepo.findById(req.menuItemId)
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found: " + req.menuItemId));

            if (item.getAvailable() == null || !item.getAvailable()) {
                throw new IllegalStateException("Item is not available: " + item.getName());
            }

            double subtotal = item.getPrice() * req.quantity;
            totalAmount += subtotal;

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(item);
            orderItem.setQuantity(req.quantity);
            orderItem.setSubtotal(subtotal);
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        CanteenOrder savedOrder = orderRepo.save(order);

        for (OrderItem oi : orderItems) {
            oi.setOrder(savedOrder);
            orderItemRepo.save(oi);
        }

        return savedOrder;
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        CanteenOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        order.setStatus(status);
        orderRepo.save(order);
    }
}
