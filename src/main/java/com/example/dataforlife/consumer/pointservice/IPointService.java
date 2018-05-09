package com.example.dataforlife.consumer.pointservice;

import java.util.ArrayList;

/**
 * Created by kokoghlanian on 09/05/2018.
 */
public interface IPointService {

    ArrayList<Double> getPointsArrayList(String data, int mChannelSelected);
}
