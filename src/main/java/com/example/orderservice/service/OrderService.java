package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    void save(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO findById(Long orderId);

    List<OrderResponseDTO> findAllByCustomerId(Long customerId);
}
