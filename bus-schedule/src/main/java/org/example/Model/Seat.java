package org.example.Model;


import jakarta.persistence.*;

@Entity
@Table
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    private int  seatNo;
    private int status;//1-booked 0-avaliable
    private String fhault;
    private String thault;
    private int scheduleId;

    public Seat(int seatId, int seatNo, int status, String fhault, String thault, int scheduleId) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.status = status;
        this.fhault = fhault;
        this.thault = thault;
        this.scheduleId = scheduleId;

    }
    public Seat(){}

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getfHault() {
        return fhault;
    }

    public void setfHault(String fHault) {
        this.fhault = fHault;
    }

    public String gettHault() {
        return thault;
    }

    public void settHault(String tHault) {
        this.thault = tHault;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
}
