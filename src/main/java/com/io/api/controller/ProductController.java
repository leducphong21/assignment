package com.io.api.controller;


import com.io.api.constant.Constant;
import com.io.api.constant.Msg;
import com.io.api.dto.response.PageResponseDTO;
import com.io.api.dto.response.Pagination;
import com.io.api.dto.response.ResponseDTO;
import com.io.api.exception.BusinessException;
import com.io.api.model.main.Product;
import com.io.api.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api(tags = "Product Management", description = "Operations pertaining to products in the application")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Create a new product", notes = "Creates a new product with the provided details")
    @PostMapping
    public ResponseDTO createProduct(
            @ApiParam(value = "Product details to be created", required = true)
            @RequestBody Product product) {
        try {
            LOGGER.info("createProduct input: {}", product.toString());
            Product res = productService.createProduct(product);
            return new ResponseDTO(Constant.SUCCESS, res, Msg.Product.CREATED_SUCCESS);
        } catch (BusinessException e) {
            return new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("createProduct error: ", e);
            return new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get product by ID", notes = "Retrieve product details by product ID")
    @GetMapping("/{id}")
    public ResponseDTO getProductById(
            @ApiParam(value = "ID of the product to retrieve", required = true)
            @PathVariable Long id) {
        try {
            LOGGER.info("getProductById: productId {}", id);
            Product product = productService.getProductById(id);
            return new ResponseDTO(Constant.SUCCESS, product, "");
        } catch (BusinessException e) {
            return new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("getProductById error: ", e);
            return new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update product", notes = "Update the details of an existing product")
    @PutMapping("/{id}")
    public ResponseDTO updateProduct(
            @ApiParam(value = "ID of the product to update", required = true)
            @PathVariable Long id,
            @ApiParam(value = "Updated product details", required = true)
            @RequestBody Product productDetails) {
        try {
            LOGGER.info("updateProduct, input: {}", productDetails);
            Product res = productService.updateProduct(id, productDetails);
            return new ResponseDTO(Constant.SUCCESS, res, Msg.Product.UPDATED_SUCCESS);
        } catch (BusinessException e) {
            return new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("updateProduct error: ", e);
            return new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete product", notes = "Delete an existing product by ID")
    @DeleteMapping("/{id}")
    public ResponseDTO deleteProduct(
            @ApiParam(value = "ID of the product to delete", required = true)
            @PathVariable Long id) {
        try {
            LOGGER.info("deleteProduct: productId {}", id);
            productService.deleteProduct(id);
            return new ResponseDTO(Constant.SUCCESS, Msg.Product.DELETED_SUCCESS);
        } catch (BusinessException e) {
            return new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("deleteProduct error: ", e);
            return  new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get all products", notes = "Retrieve a paginated list of all products")
    @GetMapping
    public PageResponseDTO getAllProducts(
            @ApiParam(value = "Page number to retrieve", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size) {

        try {
            LOGGER.info("getAllProducts: page {}, size {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> pageProduct = productService.getAllProducts(pageable);

            Pagination pagination = new Pagination();
            pagination.setCurrentPage(page);
            pagination.setPageSize(size);
            pagination.setTotalRecords(pageProduct.getTotalElements());
            pagination.setTotalPage(pageProduct.getTotalPages());

            PageResponseDTO pageResponseDTO = new PageResponseDTO();
            pageResponseDTO.setStatus(Constant.SUCCESS);
            pageResponseDTO.setData(pageProduct.getContent());
            return pageResponseDTO;
        } catch (BusinessException e) {
            return new PageResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("getAllProducts error: ", e);
            return new PageResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Search products", notes = "Search products by name or description using Full-Text Search with pagination")
    @GetMapping("/search")
    public PageResponseDTO searchProducts(
            @ApiParam(value = "Keyword to search for in name or description", required = true)
            @RequestParam String keyword,
            @ApiParam(value = "Page number to retrieve", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size) {
        try {
            LOGGER.info("searchProducts: page {}, size {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> pageProduct = productService.searchProducts(keyword, pageable);

            Pagination pagination = new Pagination();
            pagination.setCurrentPage(page);
            pagination.setPageSize(size);
            pagination.setTotalRecords(pageProduct.getTotalElements());
            pagination.setTotalPage(pageProduct.getTotalPages());

            PageResponseDTO pageResponseDTO = new PageResponseDTO();
            pageResponseDTO.setStatus(Constant.SUCCESS);
            pageResponseDTO.setData(pageProduct.getContent());
            return pageResponseDTO;
        } catch (BusinessException e) {
            return new PageResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("searchProducts error: ", e);
            return new PageResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }
}
