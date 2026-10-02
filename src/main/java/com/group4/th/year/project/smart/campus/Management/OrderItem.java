package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CanteenOrder order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    public OrderItem() {}

    public OrderItem(Long id, Integer quantity, Double subtotal, CanteenOrder order, MenuItem menuItem) {
        this.id = id;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.order = order;
        this.menuItem = menuItem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public CanteenOrder getOrder() { return order; }
    public void setOrder(CanteenOrder order) { this.order = order; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
}
