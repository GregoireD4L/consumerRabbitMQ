package com.example.dataforlife.consumer;

import com.example.dataforlife.consumer.pointservice.IPointService;
import com.example.dataforlife.consumer.model.InfluxPoint;
import com.example.dataforlife.consumer.pointservice.PointServiceImpl;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by kokoghlanian on 08/07/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceImplTest {

    private IPointService mPointService;
    final private String trame = "�_� � �_� � �\\� � �[� � �[� � �W� � �X� � �W� � �T� � �T� �  �\u0002k�\u0010�� �����\u0012�\u0004u<\u0015\u0004�    \u001E3�3�\u001B\u001E3�3�\u001C\"�\n" +
            "F8 5F 80 00 80 00 F8 5F 80 00 80 00 F8 5C 80 00 80 00 F8 5B 80 00 80 00 F8 5B 80 00 80 00 F8 57 80 00 80 00 F8 58 80 00 80 00 F8 57 80 00 80 00 F8 54 80 00 80 00 F8 54 80 00 80 00 00 F5 02 6B FE 10 FA E1 00 97 FF C4 FF B6 12 D4 04 75 3C 15 04 8D 00 00 00 00 1E 33 80 33 A1 1B 1E 33 89 33 A1 1C 22 C1";

    @Before
    public void initData(){
        mPointService = new PointServiceImpl();
    }

    @Test
    public void should_convert_data_to_influx_Point(){
       List<InfluxPoint> influxPointList = mPointService.getPointsArrayList(trame, Instant.now());
       assertThat(influxPointList, hasSize(10));
       for(int i = 0 ; i < influxPointList.size(); i++){
           Map<String,Object> influxPointMap = influxPointList.get(i).getValue();
           assertThat(influxPointMap.size(),is(19));
       }
    }
}
