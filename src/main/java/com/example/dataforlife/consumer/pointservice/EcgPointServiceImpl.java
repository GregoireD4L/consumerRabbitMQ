package com.example.dataforlife.consumer.pointservice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kokoghlanian on 09/05/2018.
 */
public class EcgPointServiceImpl implements IPointService {
    @Override
    public List<InfluxPoint> getPointsArrayList(String data, Instant time) {
        return null;
    }

/*
    @Override
    public List<InfluxPoint> getPointsArrayList(String data, Instant time, int channelSelected) {

        List<Double> dataList = this.getDataFromString(data);
        List<InfluxPoint> ecgData = createData(dataList,time, channelSelected);
        return ecgData;
    }

    private ArrayList<Double> getDataFromString(String data) {

        ArrayList<Double> mDataList = new ArrayList<>();
        if (data != null) {
            String[] dataList = data.split("\n");
            String dataDecoded = dataList[dataList.length - 1].replace(" ","");
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

    private List<InfluxPoint> createData(List<Double> dataList,Instant time, int channelSelected){
        List<InfluxPoint> dataFromChannel = new ArrayList<>();
        if(dataList.size()> 0){
            for(int i = 0; i < 10; i++){
                dataFromChannel.add(new InfluxPoint(dataList.get(3 * i + channelSelected - 1),time));
            }
        }
        return dataFromChannel;
    }
*/
}
