package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private int userId;
    private String bookingTime;
    private LocalDate bookingDate;

    private int scheduleId;
    private String source;
    private String destination;
    private int noOfTickets;
    private float fare;

    public Booking(){}

    public Booking(int bookingId, int userId, String bookingTime, LocalDate bookingDate, int scheduleId, String source, String destination, int noOfTickets,float fare) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bookingTime = bookingTime;
        this.bookingDate = bookingDate;
        this.scheduleId = scheduleId;
        this.source = source;
        this.destination = destination;
        this.noOfTickets = noOfTickets;
        this.fare=fare;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }
}
