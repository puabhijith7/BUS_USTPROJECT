package org.example.Dto;

import java.time.LocalDate;

public record TicketsDto(int ticketId, int bookingId, int userId, int seatNumber, String name, String email, String mobileNo, int age, String gender,
                         LocalDate date) {
}
