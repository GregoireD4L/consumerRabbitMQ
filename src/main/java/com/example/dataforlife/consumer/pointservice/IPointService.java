package com.example.dataforlife.consumer.pointservice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kokoghlanian on 09/05/2018.
 */
public interface IPointService {

    List<InfluxPoint> getPointsArrayList(String data, Instant time, int mChannelSelected);
}
