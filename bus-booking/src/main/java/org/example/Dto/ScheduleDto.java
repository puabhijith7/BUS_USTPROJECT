package org.example.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDto(int scheduleId, LocalDate date, LocalTime departureTime, LocalTime arrivalTime, int routeId, int busId) {
}
