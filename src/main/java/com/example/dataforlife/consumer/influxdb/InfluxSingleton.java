package com.example.dataforlife.consumer.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

public class InfluxSingleton {
    private static InfluxDB instance;


    public static InfluxDB getInstance() {
        if (instance == null) {
            instance = InfluxDBFactory.connect("http://51.38.185.205:8086", "dataforlife", "dataforlife2018");
            instance.setDatabase("dataforlifeDB");

            instance.enableBatch();
        }
        return instance;
    }
}
