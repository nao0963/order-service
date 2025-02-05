package com.example.orderservice.service;

import com.example.orderservice.dao.OrderDAO;
import com.example.orderservice.domain.OrderEntity;
import com.example.orderservice.domain.OrderProductEntity;
import com.example.orderservice.dto.OrderDetailDTO;
import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.dto.OrderResponseDTO;
import com.example.orderservice.repository.OrderDetailRepository;
import com.example.orderservice.service.producer.OrderStringProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDAO dao;
    private final ModelMapper modelMapper;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderStringProducer producer;



    @Transactional
    @Override
    public void save(OrderRequestDTO orderRequestDTO)  {

        //주문한 상품에 대한 정보를 테이블에 저장할 수 있도록 작업
        //DTO -> Entity
        List<OrderProductEntity> detail =
                orderRequestDTO.getOrderDetailDTOList().stream()
                        .map((detailDTO)->{
                            return modelMapper.map(detailDTO,OrderProductEntity.class);
                        }).collect(Collectors.toList());
        log.info("*********************************************");
        log.info("entity=>"+detail);
        log.info("*********************************************");

        //주문 생성
        //양방향관계인 경우 부모테이블과 자식테이블의 데이터를 한번에 저장할 수 있다.
        //부모테이블을 세팅하면서 자식테이블의 데이터도 같이 세팅할 수 있다.
        OrderEntity orderEntity = OrderEntity.makeOrderEntity(orderRequestDTO.getAddr()
                ,orderRequestDTO.getCustomerId(),detail);
        dao.save(orderEntity);

        //주문이 성공하면 정보를 product-service로 보내기
        //=>주문정보를 하나씩 꺼내서 넘기기 - 테스트
        //=>주문정보를 한꺼번에 넘기기 - 미션
        for(OrderDetailDTO product:orderRequestDTO.getOrderDetailDTOList()){
            log.info("주문성공한 내역=>"+product);
            producer.sendMessage(product);
        }

    }

    @Override
    public OrderResponseDTO findById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> findAllByCustomerId(Long customerId) {
        return List.of();
    }
}
