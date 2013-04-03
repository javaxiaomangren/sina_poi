package com.tuan800.sinapoi.model;

import java.math.BigDecimal;

/**
 * @auth Yang Hua
 * Date: 1/24/13 Time: 3:45 PM
 */
public class UrlTask {
    private int taskId;
    private String url ;
    private BigDecimal lat ;
    private BigDecimal lng ;
    private String status;
    private int shopId;

    public UrlTask(){}

    public UrlTask(String url, BigDecimal lat, BigDecimal lng, String status, int shopId) {
        this.url = url;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.shopId = shopId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "UrlTask{" +
                "taskId=" + taskId +
                ", url='" + url + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", status='" + status + '\'' +
                ", shopId=" + shopId +
                '}';
    }

    public String toSqlValue() {
        return "values(\'" + url + '\'' +
                "," + lat +
                "," + lng +
                ",\'" + status + '\'' +
                "," + shopId +
                ')';
    }
}
