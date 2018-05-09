package com.example.dataforlife.consumer.rabbitmqreceiver;


import com.example.dataforlife.consumer.influxdb.IInfluxDBService;
import com.example.dataforlife.consumer.pointservice.EcgPointServiceImpl;
import com.example.dataforlife.consumer.pointservice.IPointService;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

        IPointService pointService = new EcgPointServiceImpl();
        System.out.println("LOG KOKO  : " + message);
        List<Double> points  =  pointService.getPointsArrayList(message,1);
        influxDBService.createPointInInflux(points,"ecgChannelOne","1");
        //Point p = influxDBService.buildPoint(trimed[1], "", "");
        //influxDBService.write(p);
    }

}
