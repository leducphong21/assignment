package com.io.api.repository.main;

import com.io.api.model.main.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE " +
            "(:customerName IS NULL OR LOWER(o.customerName) LIKE LOWER(CONCAT('%', :customerName, '%'))) AND " +
            "(:id IS NULL OR o.id = :id)")
    Page<Order> findByCustomerNameContainingIgnoreCaseOrId(
            @Param("customerName") String customerName,
            @Param("id") Long id,
            Pageable pageable);
}
