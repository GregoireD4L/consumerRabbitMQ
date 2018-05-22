package com.example.dataforlife.consumer.influxdb;


import com.example.dataforlife.consumer.pointservice.InfluxPoint;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Point> points = new ArrayList<>();
    public void write(Point point) {
        influxDBTemplate.write(point);
    }

    public void write(List<Point> points)
    {
        influxDBTemplate.write(points);
    }


    public Point buildPoint(HashMap<String, Object> fieldMap, String measurement){
        final Point p = Point.measurement(measurement)
                .time(((Instant)fieldMap.get("time")).toEpochMilli(),TimeUnit.MILLISECONDS)
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


    public ArrayList<HashMap<String,Object>> createHashMapForPointList(List<InfluxPoint> points,String idUser){
        ArrayList<HashMap<String,Object>> resultArray = new ArrayList<>();
        for(InfluxPoint pointValue : points){
            HashMap<String,Object> PointMap = new HashMap<>();
            PointMap.putAll(pointValue.getValue());
            PointMap.put("idUser",idUser);
            PointMap.put("time",pointValue.getTimestamp().toEpochMilli());
            resultArray.add(PointMap);
        }
        return resultArray;
    }

    @Override
    public void createPointInInflux(List<InfluxPoint> pointList, String measurement, String idUser){
        if(pointList != null)
        {
            if(!pointList.isEmpty())
            {
                ArrayList<HashMap<String,Object>> ecgMap = createHashMapForPointList(pointList,idUser);
                this.points.addAll(createPoints(measurement,ecgMap));
                if(!points.isEmpty())
                {
                    if(points.size()>=50) {

                                write(points);
                            points.clear();
                        }


                }
            }
        }
    }
    /*@Override
    public void afterPropertiesSet() throws Exception {
        influxDBTemplate.createDatabase();
    }*/
}
