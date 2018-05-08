package com.example.dataforlife.consumer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;

/**
 * Created by kokoghlanian on 07/05/2018.
 */

@Data
@ConfigurationProperties("spring.rabbitmq")
public class RabbitMQProperties {

        @NotEmpty
        private String endpoint;
        @NotEmpty
        private String exchange;
        @NotEmpty
        private String queue;

}
