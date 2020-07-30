package com.hl.learnmall.mbg.modal;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class TaxiGps implements Serializable {
    private Integer id;

    private Integer event;

    private Integer status;

    private Long time;

    private Double lng;

    private Double lat;

    private Integer velocity;

    private Integer direction;

    private Integer gpsStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getGpsStatus() {
        return gpsStatus;
    }

    public void setGpsStatus(Integer gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", event=").append(event);
        sb.append(", status=").append(status);
        sb.append(", time=").append(time);
        sb.append(", lng=").append(lng);
        sb.append(", lat=").append(lat);
        sb.append(", velocity=").append(velocity);
        sb.append(", direction=").append(direction);
        sb.append(", gpsStatus=").append(gpsStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}