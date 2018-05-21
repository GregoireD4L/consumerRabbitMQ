package com.example.dataforlife.consumer.pointservice;

import java.time.Instant;

public class InfluxPoint {
   private double value;
   private Instant timestamp;

    public InfluxPoint(double value, Instant timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
