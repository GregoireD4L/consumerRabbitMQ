package com.example.dataforlife.consumer.rabbitmqreceiver;


import com.example.dataforlife.consumer.influxdb.IInfluxDBService;
import com.example.dataforlife.consumer.model.CustomMessage;
import com.example.dataforlife.consumer.pointservice.IPointService;
import com.example.dataforlife.consumer.pointservice.InfluxPoint;
import com.example.dataforlife.consumer.pointservice.PointServiceImpl;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kokoghlanian on 07/05/2018.
 */

@Service
public class ReceiveHandlerImpl implements IReceiveHandler {
    static int cpt = 0;
    static List<InfluxPoint> points ;
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
    if(points==null)
        points= new ArrayList<InfluxPoint>();
        IPointService pointService = new PointServiceImpl();
        synchronized (points) {
            points.addAll(pointService.getPointsArrayList(message.getData(), Instant.ofEpochMilli(message.getTime())));

            if (points.size() >=500) {
                        influxDBService.createPointInInflux(points, "allPoints", message.getId());

                        points.clear();

                    }

            //}
            System.out.println(++cpt + "   " + points.size() + "   " + message.getTime());
        }

    }

}
