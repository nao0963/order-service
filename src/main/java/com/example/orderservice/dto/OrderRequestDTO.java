package com.example.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private Long customerId;
    private String addr;
    private List<OrderDetailDTO> orderDetailDTOList;
}
