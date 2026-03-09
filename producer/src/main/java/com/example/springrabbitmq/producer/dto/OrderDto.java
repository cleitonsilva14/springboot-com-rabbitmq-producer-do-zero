package com.example.springrabbitmq.producer.dto;


import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto{

    @Serial
    private static final Long serialVersionUID = 1L;

    private String orderID;
    private BigDecimal quantity;
    private String description;

    // 16:45

}
