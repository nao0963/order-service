package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.dto.OrderResponseDTO;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.producer.OrderStringProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
  private final OrderService orderService;
  private final OrderStringProducer producer;

  //OrderRequestDTO는 주문에 대한 일반적인 내용과 주문하는 상품에 대한 정보를 담는 구성
    @PostMapping("/create")
    public void create(@RequestBody OrderRequestDTO order){
      log.info("주문내역=>{}",order);
      orderService.save(order);
//      System.out.println(order.toString());
    }
    @GetMapping("/getOrders/{customerId}")
    public List<OrderResponseDTO> getOrders(@PathVariable Long customerId){
     return null;

    }
}

