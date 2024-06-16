package com.io.api.controller;

import com.io.api.constant.Constant;
import com.io.api.constant.Msg;
import com.io.api.dto.response.PageResponseDTO;
import com.io.api.dto.response.Pagination;
import com.io.api.dto.response.ResponseDTO;
import com.io.api.exception.BusinessException;
import com.io.api.model.main.Order;
import com.io.api.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "Order Management", description = "Operations pertaining to orders in the application")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Create a new order", notes = "Creates a new order with the provided details")
    @PostMapping
    public ResponseDTO createOrder(
            @ApiParam(value = "Order details to be created", required = true)
            @RequestBody Order order) {
        try {
            LOGGER.info("createOrder input: {}", order.toString());
            Order res = orderService.createOrder(order);
            return new ResponseDTO(Constant.SUCCESS, res, Msg.Order.CREATED_SUCCESS);
        }catch (BusinessException e) {
            return new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("createOrder error: ", e);
            return new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get order by ID", notes = "Retrieve order details by order ID")
    @GetMapping("/{id}")
    public ResponseDTO getOrderById(
            @ApiParam(value = "ID of the order to retrieve", required = true)
            @PathVariable Long id) {
        try {
            return new ResponseDTO(Constant.SUCCESS, orderService.getOrderById(id), "");
        } catch (BusinessException e) {
            return new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("getOrderById error: ", e);
            return new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update order", notes = "Update the details of an existing order")
    @PutMapping("/{id}")
    public ResponseDTO updateOrder(
            @ApiParam(value = "ID of the order to update", required = true)
            @PathVariable Long id,
            @ApiParam(value = "Updated order details", required = true)
            @RequestBody Order orderDetails) {
        try {
            LOGGER.info("updateOrder, id: {}, order: {}", id, orderDetails);
            Order res = orderService.updateOrder(id, orderDetails);
            return new ResponseDTO(Constant.SUCCESS, res, Msg.Order.UPDATED_SUCCESS);
        } catch (BusinessException e) {
            return  new ResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("updateOrder error: ", e);
            return  new ResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "Get all orders", notes = "Retrieve a paginated list of all orders")
    @GetMapping
    public PageResponseDTO getAllOrders(
            @ApiParam(value = "Page number to retrieve", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size) {
        try {
            LOGGER.info("getAllOrders: page {}, size {}", page, size);

            Pageable pageable = PageRequest.of(page, size);
            Page<Order> pageOrder = orderService.getAllOrders(pageable);

            Pagination pagination = new Pagination();
            pagination.setPageSize(size);
            pagination.setCurrentPage(page);
            pagination.setTotalPage(pageOrder.getTotalPages());
            pagination.setTotalRecords(pageOrder.getTotalElements());

            PageResponseDTO pageResponseDTO = new PageResponseDTO();
            pageResponseDTO.setStatus(Constant.SUCCESS);
            pageResponseDTO.setData(pageOrder.getContent());
            pageResponseDTO.setPagination(pagination);
            return pageResponseDTO;
        } catch (BusinessException e) {
            return new PageResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("getAllOrders error: ", e);
            return new PageResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Search orders", notes = "Search orders by customer name or order ID")
    @GetMapping("/search")
    public PageResponseDTO searchOrders(
            @ApiParam(value = "Customer name to search for", required = false)
            @RequestParam(required = false) String customerName,
            @ApiParam(value = "Order ID to search for", required = false)
            @RequestParam(required = false) Long orderId,
            @ApiParam(value = "Page number to retrieve", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Number of records per page", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size) {
        try {
            LOGGER.info("searchOrders: customerName {}, orderId {}, page {}, size {}", customerName, orderId, page, size);
            Pageable pageable = PageRequest.of(page, size);
            Page<Order> pageOrder = orderService.search(customerName, orderId, pageable);

            Pagination pagination = new Pagination();
            pagination.setPageSize(size);
            pagination.setCurrentPage(page);
            pagination.setTotalPage(pageOrder.getTotalPages());
            pagination.setTotalRecords(pageOrder.getTotalElements());

            PageResponseDTO pageResponseDTO = new PageResponseDTO();
            pageResponseDTO.setStatus(Constant.SUCCESS);
            pageResponseDTO.setData(pageOrder.getContent());
            pageResponseDTO.setPagination(pagination);
            return pageResponseDTO;
        } catch (BusinessException e) {
            return new PageResponseDTO(Constant.ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("searchOrders error: ", e);
            return new PageResponseDTO(Constant.ERROR, Msg.INTERNAL_SERVER_ERROR);
        }
    }
}
