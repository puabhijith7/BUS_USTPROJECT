package org.example.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingDto (int bookingId, int userId, LocalTime bookingTime, LocalDate bookingDate, int scheduleId, String source, String destination, int noOfTickets,float fare){
}
