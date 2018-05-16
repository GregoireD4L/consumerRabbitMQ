package com.example.dataforlife.consumer.model;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * Created by kokoghlanian on 14/05/2018.
 */

@Data
public class CustomMessage implements Serializable {

    private String data;
    private String id;
    private Instant time;
}
