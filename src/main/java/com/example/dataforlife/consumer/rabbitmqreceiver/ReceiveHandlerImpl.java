package com.example.dataforlife.consumer.rabbitmqreceiver;


import com.example.dataforlife.consumer.influxdb.IInfluxDBService;
import com.example.dataforlife.consumer.model.CustomMessage;
import com.example.dataforlife.consumer.pointservice.EcgPointServiceImpl;
import com.example.dataforlife.consumer.pointservice.IPointService;
import com.example.dataforlife.consumer.pointservice.InfluxPoint;
import com.example.dataforlife.consumer.pointservice.PointServiceImpl;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
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
    public void handleMessage(@Payload CustomMessage message) {

        IPointService pointService = new PointServiceImpl();
        System.out.println("Consumer :: handleMessage : " + message.getData());
        System.out.println(message.getTime());
        List<InfluxPoint> points  =  pointService.getPointsArrayList(message.getData(),Instant.ofEpochMilli(message.getTime()));
        influxDBService.createPointInInflux(points,"allPoints",message.getId());

    }

}
