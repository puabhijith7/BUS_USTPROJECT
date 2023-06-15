package org.example.Dto;

import java.util.List;

public record RequestDto(String source, String destination, int scheduleId, int userId,
                         int tickets, float fare, String date,List<Integer> seatNoList,
                         List<PassengerDto> passengerDtoList)
{
}
