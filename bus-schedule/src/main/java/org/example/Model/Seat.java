package org.example.Model;


import jakarta.persistence.*;

@Entity
@Table
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seat_id;
    private int  seat_no;

    public Seat(int seat_id, int seat_no, int status, String fHault, String tHault, int schedule_id) {
        this.seat_id = seat_id;
        this.seat_no = seat_no;
        this.status = status;
        this.fHault = fHault;
        this.tHault = tHault;
        this.schedule_id = schedule_id;
    }
    public Seat(){}

    private int status;//1-booked 0-avaliable
   private String fHault;

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(int seat_no) {
        this.seat_no = seat_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getfHault() {
        return fHault;
    }

    public void setfHault(String fHault) {
        this.fHault = fHault;
    }

    public String gettHault() {
        return tHault;
    }

    public void settHault(String tHault) {
        this.tHault = tHault;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    private String tHault;
    private int schedule_id;

}
