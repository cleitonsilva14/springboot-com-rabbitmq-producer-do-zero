package com.example.springrabbitmq.producer.controller;


import com.example.springrabbitmq.producer.dto.OrderDto;
import com.example.springrabbitmq.producer.service.OrderProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderProducerService orderProducerService;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderProducerService orderProducerService){
        this.orderProducerService = orderProducerService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto order){
        log.info("Recebendo pedido: {}",order);
        orderProducerService.publishOrder(order);
        return ResponseEntity.status(CREATED).body("Pedido criado e publicado no RabbitMQ");
    }



}
