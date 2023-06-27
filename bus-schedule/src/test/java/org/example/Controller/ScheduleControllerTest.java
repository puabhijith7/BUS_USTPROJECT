package org.example.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Dto.ScheduleDto;
import org.example.Dto.SetSeatStatus;
import org.example.Model.Schedule;
import org.example.Model.Seat;
import org.example.Service.ScheduleService;
import org.example.Wrapper.BusDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService mockScheduleService;

    @Test
    void testGetbyDate() throws Exception {
// Setup
// Configure ScheduleService.getbydate(...).
        final Schedule schedule = new Schedule();
        schedule.setScheduleId(0);
        schedule.setDate(LocalDate.of(2020, 1, 1));
        schedule.setDepartureTime(LocalTime.of(0, 0, 0));
        schedule.setArrivalTime(LocalTime.of(0, 0, 0));
        schedule.setRouteId(0);
        schedule.setBusId(0);
        final List<Schedule> scheduleList = List.of(schedule);
        when(mockScheduleService.getbydate(LocalDate.of(2020, 1, 1), "source", "destination")).thenReturn(scheduleList);

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/schedules/schedule/{date}/{source}/{destination}", "2020-01-01", "source", "destination")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"scheduleId\":0,\"date\":\"2020-01-01\",\"departureTime\":\"00:00:00\",\"arrivalTime\":\"00:00:00\",\"routeId\":0,\"busId\":0}]", response.getContentAsString());
    }



    @Test
    void testGetbyDateBus() throws Exception {
// Setup
// Configure ScheduleService.getbydateBus(...).
        final List<BusDto> busDtos = List.of(new BusDto(0, 0, "regNo", "engineNo", "busType", "busName", 0));
        when(mockScheduleService.getbydateBus(LocalDate.of(2020, 1, 1), "source", "destination")).thenReturn(busDtos);

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/schedules/schedule/bus/{date}/{source}/{destination}", "2020-01-01", "source", "destination")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"busId\":0,\"busNo\":0,\"regNo\":\"regNo\",\"engineNo\":\"engineNo\",\"busType\":\"busType\",\"busName\":\"busName\",\"totalSeats\":0}]", response.getContentAsString());
    }

    @Test
    void testGetbyDateBus_ScheduleServiceReturnsNoItems() throws Exception {
// Setup
        when(mockScheduleService.getbydateBus(LocalDate.of(2020, 1, 1), "source", "destination")).thenReturn(
                Collections.emptyList());

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/schedules/schedule/bus/{date}/{source}/{destination}", "2020-01-01", "source", "destination")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

//    @Test
//    void testGetFare() throws Exception {
//// Setup
//        when(mockScheduleService.getFare(LocalDate.of(2020, 1, 1), "source", "destination")).thenReturn(List.of(0.0f));
//
//// Run the test
//        final MockHttpServletResponse response = mockMvc.perform(
//                        get("/api/v1/schedules/schedule/fare/{date}/{source}/{destination}", "2020-01-01", "source", "destination")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//// Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("[0.0]", response.getContentAsString());
//    }

    @Test
    void testGetFare_ScheduleServiceReturnsNoItems() throws Exception {
// Setup
        when(mockScheduleService.getFare(LocalDate.of(2020, 1, 1), "source", "destination")).thenReturn(
                Collections.emptyList());

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/schedules/schedule/fare/{date}/{source}/{destination}", "2020-01-01", "source", "destination")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testCreateSchedule() throws Exception {
/// Create a ScheduleDto object
        ScheduleDto scheduleDto = new ScheduleDto(
                0,                        // scheduleId
                LocalDate.of(2022, 6, 1), // date
                LocalTime.of(9, 0),       // departureTime
                LocalTime.of(12, 0),      // arrivalTime
                1,                        // routeId
                2                         // busId
        );

// Convert the ScheduleDto object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestContent = objectMapper.writeValueAsString(scheduleDto);

// Send the request and obtain the response
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/schedules/schedule/post")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
      //  assertEquals("expectedResponse", response.getContentAsString());
        verify(mockScheduleService).save(any(Schedule.class));

    }

    @Test
    void testGetSchedule() throws Exception {
        // Setup
        // Configure ScheduleService.getbyScheduleId(...).
        final Schedule schedule1 = new Schedule();
        schedule1.setScheduleId(0);
        schedule1.setDate(LocalDate.of(2020, 1, 1));
        schedule1.setDepartureTime(LocalTime.of(0, 0, 0));
        schedule1.setArrivalTime(LocalTime.of(0, 0, 0));
        schedule1.setRouteId(0);
        schedule1.setBusId(0);
        final Optional<Schedule> schedule = Optional.of(schedule1);
        when(mockScheduleService.getbyScheduleId(0)).thenReturn(schedule);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/schedules/schedule/id/{scheduleId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
       // assertEquals("expectedResponse", response.getContentAsString());
    }



// ...

    @Test
    void testGetSchedle_ScheduleServiceReturnsAbsent() throws Exception {
// Setup
        when(mockScheduleService.getbyScheduleId(0)).thenReturn(Optional.empty());

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/schedules/schedule/id/{scheduleId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
     //   assertEquals("", response.getContentAsString());
    }


    @Test
    void testGetseatsbyScheduleId() throws Exception {
        // Setup
        // Configure ScheduleService.getseatsbyScheduleId(...).
        final Seat seat = new Seat();
        seat.setSeatId(0);
        seat.setSeatNo(0);
        seat.setStatus(0);
        seat.setfHault("fHault");
        seat.settHault("tHault");
        final List<Seat> seats = List.of(seat);
        when(mockScheduleService.getseatsbyScheduleId(0)).thenReturn(seats);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/schedules/seat/{scheduleId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expectedResponse = "[{\"seatId\":0,\"seatNo\":0,\"status\":0,\"scheduleId\":0,\"tHault\":\"tHault\",\"fHault\":\"fHault\"}]";
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
    }






                                                                                                                                                                                                         @Test
    void testGetseatsbyScheduleId_ScheduleServiceReturnsNoItems() throws Exception {
// Setup
        when(mockScheduleService.getseatsbyScheduleId(0)).thenReturn(Collections.emptyList());

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/schedules/seat/{scheduleId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testSetseatstatus() throws Exception {
        // Setup
        SetSeatStatus setSeatStatus = new SetSeatStatus(0, 0, "fhault", "thault");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestContent = objectMapper.writeValueAsString(setSeatStatus);

        Seat seat = new Seat();
        seat.setSeatId(0);
        seat.setSeatNo(0);
        seat.setStatus(0);
        seat.setfHault("fHault");
        seat.settHault("tHault");

        when(mockScheduleService.getseatstatus(any(SetSeatStatus.class))).thenReturn(seat);
        when(mockScheduleService.getseatstatus1(any(SetSeatStatus.class))).thenReturn(seat);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/schedules/seat/status")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    //    assertEquals("expectedResponse", response.getContentAsString());
        verify(mockScheduleService).setstatus(any(Seat.class), any(Seat.class));
    }

}