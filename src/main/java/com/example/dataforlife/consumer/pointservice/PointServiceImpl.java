package com.example.dataforlife.consumer.pointservice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PointServiceImpl implements IPointService {

    public HashMap<String, Double> getPointsMapAccelero(String data) {

        HashMap<String, Double> dataSeriesMap = new HashMap<>();
        if (data != null) {

            String dataDecoded;
            double dataDecodedX;
            double dataDecodedY;
            double dataDecodedZ;
            String[] dataList = data.split("\n");
            dataDecoded = dataList[dataList.length - 1].replace(" ", "");
            dataDecodedX = (double) Integer.parseInt(dataDecoded.substring(24, 36).substring(0, 4), 16);
            dataDecodedY = (double) Integer.parseInt(dataDecoded.substring(24, 36).substring(4, 8), 16);
            dataDecodedZ = (double) Integer.parseInt(dataDecoded.substring(24, 36).substring(8, 12), 16);

            if (dataDecodedX > 32767) {
                dataDecodedX = (-65536 + dataDecodedX) * 2 / 32768;
            } else {
                dataDecodedX = dataDecodedX * 2 / 32767;
            }
            if (dataDecodedY > 32767) {
                dataDecodedY = (-65536 + dataDecodedY) * 2 / 32768;
            } else {
                dataDecodedY = dataDecodedY * 2 / 32767;
            }
            if (dataDecodedZ > 32767) {
                dataDecodedZ = (-65536 + dataDecodedZ) * 2 / 32768;
            } else {
                dataDecodedZ = dataDecodedZ * 2 / 32767;
            }
            dataSeriesMap.put("acceleroX1", dataDecodedX);
            dataSeriesMap.put("acceleroY1", dataDecodedY);
            dataSeriesMap.put("acceleroZ1", dataDecodedZ);
            dataDecodedX = (double) Integer.parseInt(dataDecoded.substring(12, 24).substring(0, 4), 16);
            dataDecodedY = (double) Integer.parseInt(dataDecoded.substring(12, 24).substring(4, 8), 16);
            dataDecodedZ = (double) Integer.parseInt(dataDecoded.substring(12, 24).substring(8, 12), 16);
            if (dataDecodedX > 32767) {
                dataDecodedX = (-65536 + dataDecodedX) * 250 / 32768;
            } else {
                dataDecodedX = dataDecodedX * 250 / 32767;
            }
            if (dataDecodedY > 32767) {
                dataDecodedY = (-65536 + dataDecodedY) * 250 / 32768;
            } else {
                dataDecodedY = dataDecodedY * 250 / 32767;
            }
            if (dataDecodedZ > 32767) {
                dataDecodedZ = (-65536 + dataDecodedZ) * 250 / 32768;
            } else {
                dataDecodedZ = dataDecodedZ * 250 / 32767;
            }
            dataSeriesMap.put("acceleroX2", dataDecodedX);
            dataSeriesMap.put("acceleroY2", dataDecodedY);
            dataSeriesMap.put("acceleroZ2", dataDecodedZ);
            dataDecodedX = (double) Integer.parseInt(dataDecoded.substring(0, 12).substring(0, 4), 16);
            dataDecodedY = (double) Integer.parseInt(dataDecoded.substring(0, 12).substring(4, 8), 16);
            dataDecodedZ = (double) Integer.parseInt(dataDecoded.substring(0, 12).substring(8, 12), 16);
            if (dataDecodedX > 32767) {
                dataDecodedX = (-65536 + dataDecodedX) * 2 / 32768;
            } else {
                dataDecodedX = dataDecodedX * 2 / 32767;
            }
            if (dataDecodedY > 32767) {
                dataDecodedY = (-65536 + dataDecodedY) * 2 / 32768;
            } else {
                dataDecodedY = dataDecodedY * 2 / 32767;
            }
            if (dataDecodedZ > 32767) {
                dataDecodedZ = (-65536 + dataDecodedZ) * 2 / 32768;
            } else {
                dataDecodedZ = dataDecodedZ * 2 / 32767;
            }

            dataSeriesMap.put("acceleroX3", dataDecodedX);
            dataSeriesMap.put("acceleroY3", dataDecodedY);
            dataSeriesMap.put("acceleroZ3", dataDecodedZ);
        }
        return dataSeriesMap;
    }

    public HashMap<String, Double> getPointsMapECG1(String data) {

        List<Double> dataList = this.getDataFromString(data);
        HashMap<String, Double> ecgData = createData(dataList);
        return ecgData;
    }

    private ArrayList<Double> getDataFromString(String data) {

        ArrayList<Double> mDataList = new ArrayList<>();
        if (data != null) {
            String[] dataList = data.split("\n");
            String dataDecoded = dataList[dataList.length - 1].replace(" ", "");
            for (int i = 0; i < 30; i++) {
                double mPoint = (double) Integer.parseInt(dataDecoded.substring(4 * i, 4 * i + 4), 16) * 2.4 / (32768 - 1);
                if (mPoint > 2.4) {
                    mDataList.add((-4.8 + mPoint));
                } else {
                    mDataList.add(mPoint);
                }
            }
        }
        return mDataList;
    }

    private HashMap<String, Double> createData(List<Double> dataList) {
        HashMap<String, Double> dataFromChannel = new HashMap<>();
        if (dataList.size() > 0) {
            for (int i = 0; i < 10; i++) {
                dataFromChannel.put("ecg1", dataList.get(3 * i + 1));
            }
        }
        return dataFromChannel;
    }

    @Override
    public List<InfluxPoint> getPointsArrayList(String data, Instant time) {
        List<InfluxPoint> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Double>accelero=this.getPointsMapAccelero(data);
        HashMap<String, Double>ecg1= this.getPointsMapECG1(data);
        for (String key : accelero.keySet()) {
            if (key == null || accelero.get(key) == null) {
                System.out.println("accelero");
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String key : ecg1.keySet()) {
            if (key == null || ecg1.get(key) == null) {
                System.out.println("ecg1");
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        map.putAll(accelero);
        map.putAll(ecg1);
        list.add(new InfluxPoint(map, time));
        return list;
    }
}
