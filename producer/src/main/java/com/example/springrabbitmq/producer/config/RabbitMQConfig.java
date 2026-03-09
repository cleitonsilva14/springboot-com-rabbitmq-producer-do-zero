package com.example.springrabbitmq.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.SimpleMessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order.exchange";
    public static final String QUEUE_NAME = "order.queue";
    public static final String ROUTING_KEY = "order.created";

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public SimpleMessageConverter simpleMessageConverter(){
        return new SimpleMessageConverter();
    }

    /**
     * Sincronizar as configurações no spring com o RabbitMQ
     *
     * */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    /**
     * Hook de inicialização do Spring Boot
     * */
    @Bean
    public ApplicationRunner applicationRunner(RabbitAdmin rabbitAdmin){
        return args -> rabbitAdmin.initialize();
    }


}
