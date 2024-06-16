package com.io.api.service;

import com.io.api.constant.Msg;
import com.io.api.constant.OrderStatus;
import com.io.api.exception.BusinessException;
import com.io.api.model.main.Order;
import com.io.api.repository.main.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setId(null);  // Đảm bảo id được tạo tự động
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new BusinessException(Msg.Order.NOT_FOUND);
        }
        return optionalOrder.get();
    }

    public Page<Order> search(String customerName, Long id, Pageable pageAble) {
        return orderRepository.findByCustomerNameContainingIgnoreCaseOrId(customerName, id, pageAble);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(orderDetails.getOrderDate());
                    order.setCustomerName(orderDetails.getCustomerName());
                    order.setAddress(orderDetails.getAddress());
                    order.setEmail(orderDetails.getEmail());
                    order.setPhoneNumber(orderDetails.getPhoneNumber());
                    order.setStatus(orderDetails.getStatus());
                    order.setTotalAmount(orderDetails.getTotalAmount());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new BusinessException("Order not found"));
    }

    public void deleteOrder(Long id) {
        orderRepository.findById(id)
                .ifPresent(orderRepository::delete);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

}
