package com.example.busservice1.Controller;

import com.example.busservice1.Service.BusService;
import com.example.busservice1.dto.BusDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.example.busservice1.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BusController.class)
class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusService mockBusService;

    @Autowired
    private WebApplicationContext webApplicationContext;

   // private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreate_ThrowsBusAlreadyException() throws Exception {
        // Setup
        // Configure BusService.getbybusId(...).
        final Bus bus1 = new Bus();
        bus1.setBusId(0);
        bus1.setBusNo(0);
        bus1.setRegNo("regNo");
        bus1.setEngineNo("engineNo");
        bus1.setBusType("busType");
        bus1.setBusName("busName");
        bus1.setTotalSeats(0);
        final Optional<Bus> bus = Optional.of(bus1);
        when(mockBusService.getbybusId(0)).thenReturn(bus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/buses/bus")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }


    @Test
    void testCreate_BusServiceGetbybusIdReturnsAbsent() throws Exception {
        // Setup
        when(mockBusService.getbybusId(0)).thenReturn(Optional.empty());

        // Configure BusService.save(...).
        final Bus bus = new Bus();
        bus.setBusId(0);
        bus.setBusNo(0);
        bus.setRegNo("regNo");
        bus.setEngineNo("engineNo");
        bus.setBusType("busType");
        bus.setBusName("busName");
        bus.setTotalSeats(0);
        when(mockBusService.save(any(Bus.class))).thenReturn(bus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/buses/bus")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
      //  assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllBus() throws Exception {
        // Setup
        // Configure BusService.getall(...).
        final Bus bus = new Bus();
        bus.setBusId(0);
        bus.setBusNo(0);
        bus.setRegNo("regNo");
        bus.setEngineNo("engineNo");
        bus.setBusType("busType");
        bus.setBusName("busName");
        bus.setTotalSeats(0);
        final List<Bus> buses = List.of(bus);
        when(mockBusService.getall()).thenReturn(buses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/bus")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"busId\":0,\"busNo\":0,\"regNo\":\"regNo\",\"engineNo\":\"engineNo\",\"busType\":\"busType\",\"busName\":\"busName\",\"totalSeats\":0}]");
    }

    @Test
    void testGetAllBus_BusServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockBusService.getall()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/bus")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
      //  assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetBus() throws Exception {
        // Setup
        // Configure BusService.getbybusId(...).
        final Bus bus1 = new Bus();
        bus1.setBusId(0);
        bus1.setBusNo(0);
        bus1.setRegNo("regNo");
        bus1.setEngineNo("engineNo");
        bus1.setBusType("busType");
        bus1.setBusName("busName");
        bus1.setTotalSeats(0);
        final Optional<Bus> bus = Optional.of(bus1);
        when(mockBusService.getbybusId(0)).thenReturn(bus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/bus/{busId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"busId\":0,\"busNo\":0,\"regNo\":\"regNo\",\"engineNo\":\"engineNo\",\"busType\":\"busType\",\"busName\":\"busName\",\"totalSeats\":0}");
    }

    @Test
    void testGetBus_BusServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockBusService.getbybusId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/bus/{busId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
       // assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdatebus() throws Exception {
        // Setup
        // Create a sample BusDto object
        BusDto busDto = new BusDto();
        busDto.setBusId(0);
        // Set other properties of busDto

        // Configure BusService.getbybusId(...)
        final Optional<Bus> busOpt = Optional.of(new Bus());
        when(mockBusService.getbybusId(0)).thenReturn(busOpt);

        // Configure BusService.save(...)
        when(mockBusService.save(any(Bus.class))).thenReturn(new Bus());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/buses/bus")
                        .content(objectMapper.writeValueAsString(busDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        // Add additional assertions if needed
    }

    @Test
    void testUpdatebus_BusServiceGetbybusIdReturnsAbsent() throws Exception {
        // Setup
        when(mockBusService.getbybusId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/buses/bus")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void testDeletebus() throws Exception {
        // Setup
        // Configure BusService.getbybusId(...).
        final Bus bus1 = new Bus();
        bus1.setBusId(0);
        bus1.setBusNo(0);
        bus1.setRegNo("regNo");
        bus1.setEngineNo("engineNo");
        bus1.setBusType("busType");
        bus1.setBusName("busName");
        bus1.setTotalSeats(0);
        final Optional<Bus> bus = Optional.of(bus1);
        when(mockBusService.getbybusId(0)).thenReturn(bus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/buses/bus/{busId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      //  assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockBusService).delete(0);
    }

    @Test
    void testDeletebus_BusServiceGetbybusIdReturnsAbsent() throws Exception {
        // Setup
        when(mockBusService.getbybusId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/buses/bus/{busId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }
}
