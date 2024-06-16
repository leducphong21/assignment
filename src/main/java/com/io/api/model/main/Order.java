package com.io.api.model.main;

import com.io.api.constant.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "customer_name")
    private String customerName;
    private String address;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private int statusValue;

    @Transient
    private OrderStatus status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @PostLoad
    void fillTransient() {
        this.status = OrderStatus.fromValue(this.statusValue);
    }

    @PrePersist
    @PreUpdate
    void fillPersistent() {
        this.statusValue = this.status.getValue();
    }

    public Long getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", statusValue=" + statusValue +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
