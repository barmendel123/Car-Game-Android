package com.example.cargame;

import java.io.Serializable;

public class Record implements Serializable {
    int points;
    double longTitude, altTitude;

    public Record(double longTitude, double altTitude) {
        this.longTitude = longTitude;
        this.altTitude = altTitude;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getLongTitude() {
        return longTitude;
    }

    public void setLongTitude(double longTitude) {
        this.longTitude = longTitude;
    }

    public double getAltTitude() {
        return altTitude;
    }

    public void setAltTitude(double altTitude) {
        this.altTitude = altTitude;
    }

    @Override
    public String toString() {
        return "Record{" +
                "points=" + points +
                ", longTitude=" + longTitude +
                ", altTitude=" + altTitude +
                '}';
    }
}

