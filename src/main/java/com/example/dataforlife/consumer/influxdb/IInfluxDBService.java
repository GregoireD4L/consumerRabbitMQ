package com.example.dataforlife.consumer.influxdb;

import org.influxdb.dto.Point;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
public interface IInfluxDBService {

    void write(Point point);

}
