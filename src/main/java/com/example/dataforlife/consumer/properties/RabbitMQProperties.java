package com.example.dataforlife.consumer.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import javax.validation.constraints.NotEmpty;

/**
 * Created by kokoghlanian on 07/05/2018.
 */

@Data
@ConfigurationProperties("spring.rabbitmq")
public class RabbitMQProperties {

    @NotEmpty
    @Getter
    @Value("${spring.rabbitmq.endpoint}")
    private String endpoint;// = "amqp://dataforlife:dataforlife2018@51.38.185.206:5672/%2f";

    @NotEmpty
    private String exchange;

    @NotEmpty
    private String queue;

    public @NotEmpty String getEndpoint() {
        return endpoint;
    }
}
