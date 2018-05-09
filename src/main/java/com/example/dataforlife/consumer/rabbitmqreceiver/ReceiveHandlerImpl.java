package com.example.dataforlife.consumer.rabbitmqreceiver;


import com.example.dataforlife.consumer.influxdb.IInfluxDBService;
import org.influxdb.dto.Point;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by kokoghlanian on 07/05/2018.
 */

@Service
public class ReceiveHandlerImpl implements IReceiveHandler {

    @Autowired
    IInfluxDBService influxDBService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue}", durable = "true"),
            exchange = @Exchange(value = "${spring.rabbitmq.exchange}", type = ExchangeTypes.FANOUT, durable = "true"),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
            })
    )
    @Override
    public void handleMessage(String message) {
        String[] trimed = message.split(",");
        Point p = influxDBService.buildPoint(trimed[1], "", "");
        influxDBService.write(p);
    }

}
