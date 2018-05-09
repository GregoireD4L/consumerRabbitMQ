package com.example.dataforlife.consumer.influxdb;

import com.example.dataforlife.consumer.pointservice.IPointService;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
public interface IInfluxDBService {

    void createPointInInflux(List<Double> pointList, String measurement, String idUser);
}
