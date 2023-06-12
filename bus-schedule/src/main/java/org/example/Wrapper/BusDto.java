package org.example.Wrapper;


public class BusDto {
	
    private int busId ;
    private int busNo;
    private String regNo;
    private String engineNo;
    private String busType;
    private String busName;
    private int totalSeats;


    public BusDto() {}

    public BusDto(int busId, int busNo, String regNo, String engineNo, String busType, String busName, int totalSeats) {
        this.busId = busId;
        this.busNo = busNo;
        this.regNo = regNo;
        this.engineNo = engineNo;
        this.busType = busType;
        this.busName = busName;
        this.totalSeats = totalSeats;
    }

    public int getBusId() {
        return busId;
    }

    public int getBusNo() {
        return busNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public String getBusType() {
        return busType;
    }

    public String getBusName() {
        return busName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public void setBusNo(int busNo) {
        this.busNo = busNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}

