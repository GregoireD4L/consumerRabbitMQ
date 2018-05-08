package com.example.dataforlife.consumer.influxdb;

import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
@Service
public class InfluxDBServiceImpl implements IInfluxDBService {//, InitializingBean {

    @Autowired
    InfluxDBTemplate<Point> influxDBTemplate;

    @Autowired
    InfluxDBConnectionFactory influxDBConnectionFactory;

    public void write(Point point) {
        influxDBTemplate.write(point);
    }

    public Point buildPoint(String value){
        final Point p = Point.measurement("ecg")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("dataforlife", "test")
                .addField("p1", value)
                .build();

        return p;
    }


    /*@Override
    public void afterPropertiesSet() throws Exception {
        influxDBTemplate.createDatabase();
    }*/
}
