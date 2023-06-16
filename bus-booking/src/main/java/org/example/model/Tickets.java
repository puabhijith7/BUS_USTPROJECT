package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private int bookingId;
    private int userId;

    private int seatNumber;
    private String name;
    private String email;
    private String mobileNo;
    private int age;
    private String gender;
    private LocalDate date;

    public Tickets(){}
    public Tickets(int ticketId, int bookingId, int userId, int seatNumber, String name, String email, String mobileNo, int age, String gender,LocalDate date) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.age = age;
        this.gender = gender;
        this.date=date;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
