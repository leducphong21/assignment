package com.io.api.service;

import com.io.api.constant.Msg;
import com.io.api.exception.BusinessException;
import com.io.api.model.main.Product;
import com.io.api.repository.main.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new BusinessException(Msg.Product.NOT_FOUND);
        }
        return optionalProduct.get();
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setStockQuantity(productDetails.getStockQuantity());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new BusinessException(Msg.Product.NOT_FOUND));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresent(productRepository::delete);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByFullText(keyword, pageable);
    }
}
