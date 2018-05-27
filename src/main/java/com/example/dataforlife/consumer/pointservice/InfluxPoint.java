package com.example.dataforlife.consumer.pointservice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class InfluxPoint {
   private HashMap<String,Object> value;
   private Instant timestamp;

    public InfluxPoint(HashMap<String, Object> value, Instant timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public Map<String, Object> getValue() {

        return value;
    }

    public void setValue(HashMap<String, Object> value) {
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
