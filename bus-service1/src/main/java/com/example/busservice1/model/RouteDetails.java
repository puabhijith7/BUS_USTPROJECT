package com.example.busservice1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalTime;

@Entity
@Table
public class RouteDetails {
	@Id
    private int stopId;
    private int stopNumber;
    private String hault;
    private int distFromSource;
    private LocalTime runningTime;
    private int routeId;
    public RouteDetails(){}

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getHault() {
        return hault;
    }

    public void setHault(String hault) {
        this.hault = hault;
    }

    public int getDistFromSource() {
        return distFromSource;
    }

    public void setDistFromSource(int distFromSource) {
        this.distFromSource = distFromSource;
    }

    public LocalTime getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(LocalTime runningTime) {
        this.runningTime = runningTime;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public RouteDetails(int stopId, int stopNumber, String hault, int distFromSource, LocalTime runningTime, int routeId) {
        this.stopId = stopId;
        this.stopNumber = stopNumber;
        this.hault = hault;
        this.distFromSource = distFromSource;
        this.runningTime = runningTime;
        this.routeId = routeId;
    }




}
