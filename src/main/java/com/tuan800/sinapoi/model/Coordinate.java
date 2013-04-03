package com.tuan800.sinapoi.model;

import java.math.BigDecimal;

/**
 * represent a point of lat and lng
 */
public class Coordinate {
    private BigDecimal lat ;
    private BigDecimal lng ;

    public Coordinate(BigDecimal lat, BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
