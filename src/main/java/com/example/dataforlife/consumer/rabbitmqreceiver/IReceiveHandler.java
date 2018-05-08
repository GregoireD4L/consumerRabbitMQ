package com.example.dataforlife.consumer.rabbitmqreceiver;


import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by kokoghlanian on 07/05/2018.
 */
public interface IReceiveHandler {

    void handleMessage(String message);
}
