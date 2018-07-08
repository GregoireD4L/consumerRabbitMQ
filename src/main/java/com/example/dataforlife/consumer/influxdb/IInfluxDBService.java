package com.example.dataforlife.consumer.influxdb;

import com.example.dataforlife.consumer.model.InfluxPoint;

import java.util.List;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
public interface IInfluxDBService {

    void createPointInInflux(List<InfluxPoint> pointList, String measurement, String idUser) ;
}
