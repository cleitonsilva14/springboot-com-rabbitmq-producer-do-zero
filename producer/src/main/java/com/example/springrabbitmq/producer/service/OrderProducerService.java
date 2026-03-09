package com.example.springrabbitmq.producer.service;

import com.example.springrabbitmq.producer.config.RabbitMQConfig;
import com.example.springrabbitmq.producer.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class OrderProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(OrderProducerService.class);

    public void publishOrder(OrderDto orderDto) {
        log.info("Publishing order {}", orderDto);
        try {

            byte[] payload = objectMapper.writeValueAsBytes(orderDto);

            MessageProperties  messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

            Message message = new Message(payload, messageProperties);

            rabbitTemplate.send(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
            log.info("Order published: {}", orderDto);
        }catch (Exception e){
            log.error("Error publishing order",e);
        }


    }

}
