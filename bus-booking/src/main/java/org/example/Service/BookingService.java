package org.example.Service;

import jakarta.transaction.Transactional;
import org.example.Dto.PassengerDto;
import org.example.Dto.RequestDto;
import org.example.Repo.BookingRepo;
import org.example.Repo.TicketsRepo;
import org.example.model.Booking;
import org.example.model.Tickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TicketsRepo ticketsRepo;
    @Transactional
    public Integer save(RequestDto requestDto) {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setBookingTime(LocalTime.now());
        booking.setSource(requestDto.source());
        booking.setDestination(requestDto.destination());
        booking.setScheduleId(requestDto.scheduleId());
        booking.setUserId(requestDto.userId());
        booking.setNoOfTickets(requestDto.tickets());
        booking.setFare(requestDto.fare() * booking.getNoOfTickets());
        bookingRepo.save(booking);
        booktickets(booking, requestDto.date(), requestDto.passengerDtoList(), requestDto.seatNoList());
        return booking.getBookingId();
    }

    public void booktickets(Booking booking, String date, List<PassengerDto> passengerDtoList, List<Integer> seatNoList) {
        for (int i = 0; i < booking.getNoOfTickets(); i++) {
            Tickets t = new Tickets();
            t.setBookingId(booking.getBookingId());
            t.setUserId(booking.getUserId());
            t.setName(passengerDtoList.get(i).name());
            t.setEmail(passengerDtoList.get(i).email());
            t.setMobileNo(passengerDtoList.get(i).mobileNo());
            t.setAge(passengerDtoList.get(i).age());
            t.setGender(passengerDtoList.get(i).gender());
            t.setSeatNumber(seatNoList.get(i));
            t.setDate(LocalDate.parse(date));
            ticketsRepo.save(t);
        }
    }


}
