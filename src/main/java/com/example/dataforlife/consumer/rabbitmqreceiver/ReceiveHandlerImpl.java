package com.example.dataforlife.consumer.rabbitmqreceiver;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;


/**
 * Created by kokoghlanian on 07/05/2018.
 */

@Service
public class ReceiveHandlerImpl implements IReceiveHandler {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue}", durable = "true"),
            exchange = @Exchange(value = "${spring.rabbitmq.exchange}", type = ExchangeTypes.HEADERS, durable = "true"),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
            })
    )

    @Override
    public void handleMessage(String message) {

    }
}
