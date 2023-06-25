package org.example.Controller;

import org.example.Dto.BookingDto;
import org.example.Dto.RequestDto;
import org.example.Service.BookingService;
import org.example.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    public Booking convertToEntity(BookingDto bookingDto){
        return new Booking(bookingDto.bookingId(), bookingDto.userId(), bookingDto.bookingTime(),bookingDto.bookingDate(),bookingDto.scheduleId(), bookingDto.source(), bookingDto.destination(), bookingDto.noOfTickets(), bookingDto.fare());
    }
    public BookingDto convertToDto(Booking booking){
        return new BookingDto(booking.getBookingId(), booking.getUserId(), booking.getBookingTime(),booking.getBookingDate(),booking.getScheduleId(), booking.getSource(), booking.getDestination(), booking.getNoOfTickets(), booking.getFare());
    }

    @PostMapping("/booking")
    public ResponseEntity<Integer> postBooking(@RequestBody RequestDto requestDto)
    {
       int a= bookingService.save(requestDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }
    @GetMapping("/booking")
    public ResponseEntity<List<Booking>> getall()
    {
        List list=bookingService.getall();
        return ResponseEntity.ok().body(list);
    }



}
