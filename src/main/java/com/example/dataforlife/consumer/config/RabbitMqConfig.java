package com.example.dataforlife.consumer.config;

import com.example.dataforlife.consumer.properties.RabbitMQProperties;
import com.example.dataforlife.consumer.rabbitmqreceiver.ReceiveHandlerImpl;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.net.URI;

/**
 * Created by kokoghlanian on 07/05/2018.
 */
@Configuration
@EnableRabbit
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMqConfig {

    @Autowired
    RabbitMQProperties rabbitMQProperties;

    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public ConnectionFactory connectionFactory() {
            ConnectionFactory connectionFactory = new CachingConnectionFactory(URI.create(rabbitMQProperties.getEndpoint()));
        return connectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2MessageConverter());
        factory.setConcurrentConsumers(100);
        factory.setMaxConcurrentConsumers(200);
        return factory;
    }


    @Bean
    public ReceiveHandlerImpl receiveHandler() {
        return new ReceiveHandlerImpl();
    }

}
