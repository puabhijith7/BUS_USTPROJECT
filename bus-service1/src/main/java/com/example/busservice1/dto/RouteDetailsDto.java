package com.example.busservice1.dto;




public class RouteDetailsDto {
	
    private int stopId;
    private int stopNumber;
    private String hault;
    private int runningTime;
    private int routeId;
    public RouteDetailsDto(){}

    public RouteDetailsDto(int stopId, int stopNumber, String hault, int runningTime, int routeId) {
        this.stopId = stopId;
        this.stopNumber = stopNumber;
        this.hault = hault;
        this.runningTime = runningTime;
        this.routeId = routeId;
    }

    public int getStopId() {
        return stopId;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public String getHault() {
        return hault;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public void setHault(String hault) {
        this.hault = hault;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
