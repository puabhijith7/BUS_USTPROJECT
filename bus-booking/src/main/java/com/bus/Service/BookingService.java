package com.bus.Service;

import com.bus.Dto.PassengerDto;
import com.bus.Dto.RequestDto;
import com.bus.Dto.SetSeatStatus;
import jakarta.transaction.Transactional;
import com.bus.Feign.BusScheduleFeign;
import com.bus.Repo.BookingRepo;
import com.bus.Repo.TicketsRepo;
import com.bus.model.Booking;
import com.bus.model.Tickets;
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
    @Autowired
    BusScheduleFeign busScheduleFeign;
    @Transactional
    public Integer save(RequestDto requestDto) {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
       // ZoneId zoneId = ZoneId.of("Asia/Kolkata");
      //  LocalDateTime localDateTime = LocalDateTime.now();
        booking.setBookingTime(LocalTime.now().toString());
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
            System.out.println("hello");
            setseatstatus(t,booking);
        }
    }


    public void setseatstatus(Tickets t, Booking booking) {
        System.out.println("hai");
        SetSeatStatus sss=new SetSeatStatus(t.getSeatNumber(),booking.getScheduleId(), booking.getSource(), booking.getDestination());
        busScheduleFeign.setseatstatus(sss);
    }


    public List getall() {
        return bookingRepo.findAll();
    }
}
