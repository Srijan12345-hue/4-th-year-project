package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.CanteenOrder;
import com.group4.th.year.project.smart.campus.Management.Repo.CanteenOrderRepo;
import com.group4.th.year.project.smart.campus.Management.Service.CanteenService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/canteen-orders")
public class CanteenOrderController {
    private final CanteenOrderRepo orderRepo;
    private final CanteenService canteenService;

    public CanteenOrderController(CanteenOrderRepo orderRepo, CanteenService canteenService) {
        this.orderRepo = orderRepo;
        this.canteenService = canteenService;
    }

    @Cacheable(value = "canteenOrders", key = "'all'")
    @GetMapping
    public List<CanteenOrder> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanteenOrder> getOrderById(@PathVariable Long id) {
        return orderRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "canteenOrders", allEntries = true)
    @PostMapping
    public ResponseEntity<CanteenOrder> createOrder(@RequestBody Map<String, Object> request) {
        try {
            CanteenOrder order = new CanteenOrder();

            // Handle student association
            if (request.containsKey("student")) {
                Object studentObj = request.get("student");
                if (studentObj instanceof Map<?, ?> studentMap) {
                    com.group4.th.year.project.smart.campus.Management.Student student = new com.group4.th.year.project.smart.campus.Management.Student();
                    Object idObj = studentMap.get("id");
                    if (idObj != null) {
                        student.setId(Long.valueOf(idObj.toString()));
                    }
                    order.setStudent(student);
                }
            }

            // Handle order items
            Object itemsObj = request.get("items");
            java.util.List<CanteenService.OrderItemRequest> itemRequests = new java.util.ArrayList<>();
            if (itemsObj instanceof List<?> itemsList) {
                for (Object item : itemsList) {
                    if (item instanceof Map<?, ?> itemMap) {
                        Object menuItemId = itemMap.get("menuItemId");
                        Object quantity = itemMap.get("quantity");
                        if (menuItemId != null && quantity != null) {
                            itemRequests.add(new CanteenService.OrderItemRequest(
                                Long.valueOf(menuItemId.toString()),
                                Integer.valueOf(quantity.toString())
                            ));
                        }
                    }
                }
            }

            CanteenOrder saved = canteenService.createOrder(order, itemRequests);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CacheEvict(value = "canteenOrders", allEntries = true)
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        if (status == null) return ResponseEntity.badRequest().build();
        try {
            canteenService.updateOrderStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CacheEvict(value = "canteenOrders", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
