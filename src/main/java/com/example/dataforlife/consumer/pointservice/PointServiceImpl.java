package com.example.dataforlife.consumer.pointservice;

import com.example.dataforlife.consumer.model.InfluxPoint;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            dataDecodedX = (double) Integer.parseInt(dataDecoded.substring(148, 160).substring(0, 4), 16);
            dataDecodedY = (double) Integer.parseInt(dataDecoded.substring(148, 160).substring(4, 8), 16);
            dataDecodedZ = (double) Integer.parseInt(dataDecoded.substring(148, 160).substring(8, 12), 16);

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
            dataSeriesMap.put("acceleroX", dataDecodedX);
            dataSeriesMap.put("acceleroY", dataDecodedY);
            dataSeriesMap.put("acceleroZ", dataDecodedZ);
            dataDecodedX = (double) Integer.parseInt(dataDecoded.substring(136, 148).substring(0, 4), 16);
            dataDecodedY = (double) Integer.parseInt(dataDecoded.substring(136, 148).substring(4, 8), 16);
            dataDecodedZ = (double) Integer.parseInt(dataDecoded.substring(136, 148).substring(8, 12), 16);
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
            dataSeriesMap.put("GyrosX", dataDecodedX);
            dataSeriesMap.put("GyrosY", dataDecodedY);
            dataSeriesMap.put("GyrozZ", dataDecodedZ);
            dataDecodedX = (double) Integer.parseInt(dataDecoded.substring(124, 136).substring(0, 4), 16);
            dataDecodedY = (double) Integer.parseInt(dataDecoded.substring(124, 136).substring(4, 8), 16);
            dataDecodedZ = (double) Integer.parseInt(dataDecoded.substring(124, 136).substring(8, 12), 16);
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

            dataSeriesMap.put("magnetoX", dataDecodedX);
            dataSeriesMap.put("magnetoY", dataDecodedY);
            dataSeriesMap.put("magnetoZ", dataDecodedZ);
        }
        return dataSeriesMap;
    }

    public HashMap<String, Double> getPointsMapECG(String data, int channelSelected,int index) {

        List<Double> dataList = this.getDataFromString(data);
        HashMap<String, Double> ecgData = createData(dataList,channelSelected,index);
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

    private HashMap<String, Double> createData(List<Double> dataList, int channelSelected,int index) {
        HashMap<String, Double> dataFromChannel = new HashMap<>();
        if (dataList.size() > 3 * index + channelSelected - 1) {

                dataFromChannel.put("ecg"+channelSelected, dataList.get(3 * index + channelSelected - 1));

        }
        return dataFromChannel;
    }

    private HashMap<String, Double> getPointsMapRespiration(String data){

        HashMap<String, Double> dataSeriesMap = new HashMap<>();

        if (data != null) {
            final String[] dataList = data.split("\n");
            final String dataDecoded = dataList[dataList.length - 1].replace(" ", "");
            final double dataDecodedT = Integer.parseInt(dataDecoded.substring(120, 124), 16);
            final double dataDecodedA = Integer.parseInt(dataDecoded.substring(124, 128), 16);
            dataSeriesMap.put("respiThorax",dataDecodedT);
            dataSeriesMap.put("respiAbdominal",dataDecodedA);
        }
        return dataSeriesMap;
    }

    private HashMap<String, Double> getPointsMapSpo2Chan1(String data) {

        HashMap<String, Double> dataSeriesMap = new HashMap<>();
        if (data != null) {

            final String[] dataList = data.split("\n");
            final String dataDecoded = dataList[dataList.length - 1].replace(" ", "");

            double dataDecodedR1MSB = Integer.parseInt(dataDecoded.substring(173, 174), 16);
            double dataDecodedR1LSB = Integer.parseInt(dataDecoded.substring(174, 178), 16);
            double dataDecodedR2MSB = Integer.parseInt(dataDecoded.substring(185, 186), 16);
            double dataDecodedR2LSB = Integer.parseInt(dataDecoded.substring(186, 190), 16);


            dataSeriesMap.put("Spo2Chan1-1",dataDecodedR1LSB + ((dataDecodedR1MSB % 2) + ((dataDecodedR1MSB - (dataDecodedR1MSB % 2)) % 4)) * 65536);
            dataSeriesMap.put("SpO2Chan1-2",dataDecodedR2LSB + ((dataDecodedR2MSB % 2) + ((dataDecodedR2MSB - (dataDecodedR2MSB % 2)) % 4)) * 65536);
        }
        return dataSeriesMap;
    }

    private HashMap<String, Double> getPointsMapSpo2Chan2(String data) {

        HashMap<String, Double> dataSeriesMap = new HashMap<>();
        if (data != null) {

            final String[] dataList = data.split("\n");
            final String dataDecoded = dataList[dataList.length - 1].replace(" ", "");

            double dataDecodedIr1MSB = Integer.parseInt(dataDecoded.substring(179, 180), 16);
            double dataDecodedIr1LSB = Integer.parseInt(dataDecoded.substring(180, 184), 16);
            double dataDecodedIr2MSB = Integer.parseInt(dataDecoded.substring(191, 192), 16);
            double dataDecodedIr2LSB = Integer.parseInt(dataDecoded.substring(192, 196), 16);

            dataSeriesMap.put("Spo2Chan2-1",dataDecodedIr1LSB + ((dataDecodedIr1MSB % 2) + ((dataDecodedIr1MSB - (dataDecodedIr1MSB % 2)) % 4)) * 65536);
            dataSeriesMap.put("Spo2Chan2-2",dataDecodedIr2LSB + ((dataDecodedIr2MSB % 2) + ((dataDecodedIr2MSB - (dataDecodedIr2MSB % 2)) % 4)) * 65536);
        }
        return dataSeriesMap;
    }


    private HashMap<String, Double> getPointsMapTemperature(String data){

        HashMap<String, Double> dataSeriesMap = new HashMap<>();
        if (data != null) {
            final String[] dataList = data.split("\n");
            final String dataDecoded = dataList[dataList.length - 1].replace(" ", "");
            final double dataDecodedTemp = Integer.parseInt(dataDecoded.substring(196, 200), 16);
            dataSeriesMap.put("temp",dataDecodedTemp/256);
        }
        return dataSeriesMap;
    }

    @Override
    public List<InfluxPoint> getPointsArrayList(String data, Instant time) {
        List<InfluxPoint> list = new ArrayList<>();
        long timeOfInstant = time.toEpochMilli();
        for(int i=0;i<10;i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.putAll(this.getPointsMapAccelero(data));
            map.putAll(this.getPointsMapECG(data, 1, i));
            map.putAll(this.getPointsMapECG(data, 2, i));
            map.putAll(this.getPointsMapECG(data, 3, i));
            map.putAll(this.getPointsMapRespiration(data));
            map.putAll(this.getPointsMapTemperature(data));
            map.putAll(this.getPointsMapSpo2Chan1(data));
            map.putAll(this.getPointsMapSpo2Chan2(data));

            list.add(new InfluxPoint(map, Instant.ofEpochMilli(timeOfInstant)));
            timeOfInstant+=2;


        }
        return list;
    }
}
