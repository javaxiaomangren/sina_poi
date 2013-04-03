package com.tuan800.sinapoi.model;

import java.util.Date;

/**
 * Define a fetch task
 * @author Yang Hua
 */
public class Task {
    private int taskId ;
    /*hui800 shop id, shopId=0 means the basic coordinate is not from hui800*/
    private int shopId ;
    /* basic point ,location of (lat, lng)*/
    private Coordinate coordinate ;
    private Date createDate ;
    private String lastTime ;
    private String date_fmt ;
    private String status;
    /* fetch which city's task */
    private String city ;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getDate_fmt() {
        return date_fmt;
    }

    public void setDate_fmt(String date_fmt) {
        this.date_fmt = date_fmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", shopId=" + shopId +
                ", coordinate=" + coordinate +
                ", createDate=" + createDate +
                ", lastTime='" + lastTime + '\'' +
                ", date_fmt='" + date_fmt + '\'' +
                ", status='" + status + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
