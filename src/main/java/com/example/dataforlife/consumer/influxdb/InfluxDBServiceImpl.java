package com.example.dataforlife.consumer.influxdb;

import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
public class InfluxDBServiceImpl implements IInfluxDBService, InitializingBean {

    @Autowired
    InfluxDBTemplate<Point> influxDBTemplate;

    public void write(Point point) {
        influxDBTemplate.write(point);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        influxDBTemplate.createDatabase();
    }
}
