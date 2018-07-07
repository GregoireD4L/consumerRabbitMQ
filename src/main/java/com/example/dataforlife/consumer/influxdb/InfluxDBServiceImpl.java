package com.example.dataforlife.consumer.influxdb;


import com.example.dataforlife.consumer.pointservice.InfluxPoint;
import com.example.dataforlife.consumer.security.Encrypter;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by kokoghlanian on 08/05/2018.
 */
@Service
public class InfluxDBServiceImpl implements IInfluxDBService {//, InitializingBean {

    static int cpt = 0;
    @Autowired
    InfluxDBTemplate<Point> influxDBTemplate;

    @Autowired
    InfluxDBConnectionFactory influxDBConnectionFactory;


    public void write(Point point) {
        InfluxDB influxDB = InfluxSingleton.getInstance();
        //influxDBTemplate.write(point);
        influxDB.write(point);
        System.out.println(point.toString());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write(List<Point> points) {
        influxDBTemplate.write(points);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write(BatchPoints bp) {
        InfluxDB influxDB = InfluxSingleton.getInstance();
        influxDB.write(bp);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Point buildPoint(HashMap<String, Object> fieldMap, String measurement) {
        final Point p = Point.measurement(measurement)
                .time((long) fieldMap.get("time"), TimeUnit.MILLISECONDS)
                .fields(fieldMap)
                .build();

        return p;
    }


    public List<Point> createPoints(String table, ArrayList<HashMap<String, Object>> fieldMap) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < fieldMap.size(); i++) {

        }
        return points;
    }


    public ArrayList<HashMap<String, Object>> createHashMapForPointList(List<InfluxPoint> points, String idUser) {
        ArrayList<HashMap<String, Object>> resultArray = new ArrayList<>();
        for (InfluxPoint pointValue : points) {
            HashMap<String, Object> PointMap = new HashMap<>();
            PointMap.putAll(pointValue.getValue());
            PointMap.put("idUser", idUser);
            PointMap.put("time", pointValue.getTimestamp().toEpochMilli());
            resultArray.add(PointMap);
        }
        return resultArray;
    }

    @Override
    public void createPointInInflux(List<InfluxPoint> pointList, String measurement, String idUser) {

        if (pointList != null) {
            if (!pointList.isEmpty()) {

                    createPoints(measurement, pointList, idUser);


                //if (!points.isEmpty()) {


                //  write(bp);


                        /* try {
                             System.out.println("in");
                             while(t.isAlive())
                                 TimeUnit.SECONDS.sleep(2);
                             System.out.println("out");
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }*/

                //}


            }
        }
    }

    private void createPoints(String measurement, List<InfluxPoint> pointList, String idUser) {
        InfluxDB influxDB = InfluxSingleton.getInstance();
        BatchPoints batchPoints = BatchPoints.database("dataforlifeDB").build();
        int cpt=0;
        for (InfluxPoint point : pointList) {
            cpt++;
            Point p = null;
            try {
                p = Point.measurement(measurement).time(point.getTimestamp().toEpochMilli(), TimeUnit.MILLISECONDS).fields(point.getValue()).addField("ID", Encrypter.encrypt(idUser)).addField("timestamp", point.getTimestamp().toEpochMilli()).build();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            batchPoints.point(p);


        }
      //  System.out.println(batchPoints);
       // System.out.println("batchpoint : " + batchPoints.getPoints().size() + "           " + pointList.size()+"     "+batchPoints.getPoints().get(0).toString()+"     "+batchPoints.getPoints().get(1).toString());



        influxDB.write(batchPoints);
        influxDB.flush();


    }
    /*@Override
    public void afterPropertiesSet() throws Exception {
        influxDBTemplate.createDatabase();
    }*/
}
