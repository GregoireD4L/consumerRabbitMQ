package com.example.dataforlife.consumer.rabbitmqreceiver;


import com.example.dataforlife.consumer.model.CustomMessage;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by kokoghlanian on 07/05/2018.
 */
public interface IReceiveHandler {

    void handleMessage(@Payload CustomMessage message);
}
