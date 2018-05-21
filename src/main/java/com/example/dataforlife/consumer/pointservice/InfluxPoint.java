package com.example.dataforlife.consumer.pointservice;

import java.time.Instant;
import java.util.HashMap;

public class InfluxPoint {
   private HashMap<String,Double> value;
   private Instant timestamp;

    public InfluxPoint(HashMap<String, Double> value, Instant timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public HashMap<String, Double> getValue() {

        return value;
    }

    public void setValue(HashMap<String, Double> value) {
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
