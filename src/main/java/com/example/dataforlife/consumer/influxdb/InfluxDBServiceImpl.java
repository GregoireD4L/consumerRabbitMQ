package com.example.dataforlife.consumer.influxdb;


import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
@Service
public class InfluxDBServiceImpl implements IInfluxDBService {//, InitializingBean {
    List<Point> points = new ArrayList<>();
    @Autowired
    InfluxDBTemplate<Point> influxDBTemplate;

    @Autowired
    InfluxDBConnectionFactory influxDBConnectionFactory;

    public void write(Point point) {
        influxDBTemplate.write(point);
    }

    public void write(List<Point> points)
    {
        influxDBTemplate.write(points);
    }


    public Point buildPoint(HashMap<String, Object> fieldMap, String measurement){
        final Point p = Point.measurement(measurement)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .fields(fieldMap)
                .build();

        return p;
    }


    public List<Point> createPoints(String table, ArrayList<HashMap<String, Object>> fieldMap){
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < fieldMap.size(); i++){
            points.add(buildPoint(fieldMap.get(i),table));
        }
        return points;
    }


    public ArrayList<HashMap<String,Object>> createHashMapForPointList(List<Double> points,String idUser){
        ArrayList<HashMap<String,Object>> resultArray = new ArrayList<>();
        for(Double pointValue : points){
            HashMap<String,Object> PointMap = new HashMap<>();
            PointMap.put("p1",pointValue);
            PointMap.put("idUser",idUser);
            resultArray.add(PointMap);
        }
        return resultArray;
    }

    @Override
    public void createPointInInflux(List<Double> pointList, String measurement,String idUser){
        if(pointList != null)
        {
            if(!pointList.isEmpty())
            {
                ArrayList<HashMap<String,Object>> ecgMap = createHashMapForPointList(pointList,idUser);
                if(points.isEmpty())
                    points = createPoints(measurement,ecgMap);
                else
                    points.addAll(createPoints(measurement,ecgMap));
                if(!points.isEmpty()&& points.size()>=50)
                {
                    write(points);
                    points.clear();
                }
            }
        }
    }
    /*@Override
    public void afterPropertiesSet() throws Exception {
        influxDBTemplate.createDatabase();
    }*/
}
