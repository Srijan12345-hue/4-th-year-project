package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "canteen_orders")
public class CanteenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status; // PENDING, COMPLETED, CANCELLED

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public CanteenOrder() {}

    public CanteenOrder(Long id, LocalDateTime orderDate, Double totalAmount, String status, Student student) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.student = student;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
