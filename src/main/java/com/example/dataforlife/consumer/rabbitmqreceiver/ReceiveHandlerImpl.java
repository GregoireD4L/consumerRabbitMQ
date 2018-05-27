package com.example.dataforlife.consumer.rabbitmqreceiver;


import com.example.dataforlife.consumer.influxdb.IInfluxDBService;
import com.example.dataforlife.consumer.influxdb.InfluxSingleton;
import com.example.dataforlife.consumer.model.CustomMessage;
import com.example.dataforlife.consumer.pointservice.EcgPointServiceImpl;
import com.example.dataforlife.consumer.pointservice.IPointService;
import com.example.dataforlife.consumer.pointservice.InfluxPoint;
import com.example.dataforlife.consumer.pointservice.PointServiceImpl;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Hex;
/**
 * Created by kokoghlanian on 07/05/2018.
 */

@Service
public class ReceiveHandlerImpl implements IReceiveHandler {
    static int cpt =0;
    @Autowired
    IInfluxDBService influxDBService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue}", durable = "true"),
            exchange = @Exchange(value = "${spring.rabbitmq.exchange}", type = ExchangeTypes.DIRECT, durable = "true"),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
            })
    )
    @Override
    public void handleMessage(@Payload CustomMessage message) {

       IPointService pointService = new PointServiceImpl();
        List<InfluxPoint> points  =  pointService.getPointsArrayList(message.getData(),Instant.ofEpochMilli(message.getTime()));


        influxDBService.createPointInInflux(points,"allPoints",message.getId());


    }

}
