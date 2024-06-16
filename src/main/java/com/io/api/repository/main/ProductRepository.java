package com.io.api.repository.main;

import com.io.api.model.main.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products WHERE MATCH(name, description) AGAINST (:keyword IN BOOLEAN MODE)",
            countQuery = "SELECT count(*) FROM products WHERE MATCH(name, description) AGAINST (:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<Product> searchByFullText(@Param("keyword") String keyword, Pageable pageable);
}
