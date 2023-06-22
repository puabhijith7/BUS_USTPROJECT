package com.example.busservice1.Controller;

import com.example.busservice1.Service.RouteDetailsService;
import com.example.busservice1.dto.RouteDetailsDto;
import com.example.busservice1.model.RouteDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RouteDetailsController.class)
class RouteDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteDetailsService mockRouteDetailsService;
    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void testFindDistinctRouteIdsByHault() throws Exception {
        // Setup
        when(mockRouteDetailsService.findDistinctRouteIdsByHault("source", "destination")).thenReturn(List.of(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/buses/routeDetails/r/{source}/{destination}", "source", "destination")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[0]");
    }

    @Test
    void testFindDistinctRouteIdsByHault_RouteDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRouteDetailsService.findDistinctRouteIdsByHault("source", "destination"))
                .thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/buses/routeDetails/r/{source}/{destination}", "source", "destination")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testCreate_ThrowsRouteDetailsAlreadyExistException() throws Exception {
        // Setup
        // Configure RouteDetailsService.getbystopId(...).
        final RouteDetails routeDetails1 = new RouteDetails();
        routeDetails1.setStopId(0);
        routeDetails1.setStopNumber(0);
        routeDetails1.setHault("hault");
        routeDetails1.setDistFromSource(0);
        routeDetails1.setRunningTime(LocalTime.of(0, 0, 0));
        routeDetails1.setRouteId(0);
        final Optional<RouteDetails> routeDetails = Optional.of(routeDetails1);
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(routeDetails);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/buses/routeDetails")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void testCreate_RouteDetailsServiceGetbystopIdReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(Optional.empty());

        // Configure RouteDetailsService.save(...).
        final RouteDetails routeDetails = new RouteDetails();
        routeDetails.setStopId(0);
        routeDetails.setStopNumber(0);
        routeDetails.setHault("hault");
        routeDetails.setDistFromSource(0);
        routeDetails.setRunningTime(LocalTime.of(0, 0, 0));
        routeDetails.setRouteId(0);
        when(mockRouteDetailsService.save(any(RouteDetails.class))).thenReturn(routeDetails);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/buses/routeDetails")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
     //   assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllRouteDetails() throws Exception {
        // Setup
        // Configure RouteDetailsService.getall(...).
        final RouteDetails routeDetails1 = new RouteDetails();
        routeDetails1.setStopId(0);
        routeDetails1.setStopNumber(0);
        routeDetails1.setHault("hault");
        routeDetails1.setDistFromSource(0);
        routeDetails1.setRunningTime(LocalTime.of(0, 0, 0));
        routeDetails1.setRouteId(0);
        final List<RouteDetails> routeDetails = List.of(routeDetails1);
        when(mockRouteDetailsService.getall()).thenReturn(routeDetails);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"stopId\":0,\"stopNumber\":0,\"hault\":\"hault\",\"distFromSource\":0,\"runningTime\":\"00:00:00\",\"routeId\":0}]");

    }

    @Test
    void testGetAllRouteDetails_RouteDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRouteDetailsService.getall()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testGetrouteDetails() throws Exception {
        // Setup
        // Configure RouteDetailsService.getbystopId(...).
        final RouteDetails routeDetails1 = new RouteDetails();
        routeDetails1.setStopId(0);
        routeDetails1.setStopNumber(0);
        routeDetails1.setHault("hault");
        routeDetails1.setDistFromSource(0);
        routeDetails1.setRunningTime(LocalTime.of(0, 0, 0));
        routeDetails1.setRouteId(0);
        final Optional<RouteDetails> routeDetails = Optional.of(routeDetails1);
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(routeDetails);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/{stopId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"stopId\":0,\"stopNumber\":0,\"hault\":\"hault\",\"distFromSource\":0,\"runningTime\":\"00:00:00\",\"routeId\":0}");

    }

    @Test
    void testGetrouteDetails_RouteDetailsServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/{stopId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testUpdateroute() throws Exception {
        // Setup
        // Configure routeDetailsService.getbystopId(...).
        final RouteDetails routeDetails = new RouteDetails();
        routeDetails.setStopId(0);
        routeDetails.setStopNumber(0);
        routeDetails.setHault("hault");
        routeDetails.setDistFromSource(0);
        routeDetails.setRunningTime(LocalTime.parse("00:00:00")); // Parse LocalTime from String

        final Optional<RouteDetails> routeOpt = Optional.of(routeDetails);
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(routeOpt);

        // Configure routeDetailsService.save(...).
        final RouteDetails saved = new RouteDetails();
        saved.setStopId(0);
        saved.setStopNumber(0);
        saved.setHault("hault");
        saved.setDistFromSource(0);
        saved.setRunningTime(LocalTime.parse("00:00:00")); // Parse LocalTime from String
        when(mockRouteDetailsService.save(any(RouteDetails.class))).thenReturn(saved);

        // Create and configure ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Enable support for Java 8 date/time types

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/buses/routeDetails")
                        .content(objectMapper.writeValueAsString(routeDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testUpdateroute_RouteDetailsServiceGetbystopIdReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/buses/routeDetails")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    //    assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testDeleteroute() throws Exception {
        // Setup
        // Configure RouteDetailsService.getbystopId(...).
        final RouteDetails routeDetails1 = new RouteDetails();
        routeDetails1.setStopId(0);
        routeDetails1.setStopNumber(0);
        routeDetails1.setHault("hault");
        routeDetails1.setDistFromSource(0);
        routeDetails1.setRunningTime(LocalTime.of(0, 0, 0));
        routeDetails1.setRouteId(0);
        final Optional<RouteDetails> routeDetails = Optional.of(routeDetails1);
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(routeDetails);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/buses/routeDetails/{stopId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockRouteDetailsService).delete(0);
    }

    @Test
    void testDeleteroute_RouteDetailsServiceGetbystopIdReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteDetailsService.getbystopId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/buses/routeDetails/{stopId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testSearchforStopsbyRouteId() throws Exception {
        // Setup
        when(mockRouteDetailsService.searchforStopsbyRouteId(0)).thenReturn(List.of(0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/r/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[0]");
    }

    @Test
    void testSearchforStopsbyRouteId_RouteDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRouteDetailsService.searchforStopsbyRouteId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/r/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetrouteDetailsByRouteId() throws Exception {
        // Setup
        // Configure RouteDetailsService.getbyrouteId(...).
        final RouteDetails routeDetails1 = new RouteDetails();
        routeDetails1.setStopId(0);
        routeDetails1.setStopNumber(0);
        routeDetails1.setHault("hault");
        routeDetails1.setDistFromSource(0);
        routeDetails1.setRunningTime(LocalTime.of(0, 0, 0));
        routeDetails1.setRouteId(0);
        final List<RouteDetails> routeDetails = List.of(routeDetails1);
        when(mockRouteDetailsService.getbyrouteId(0)).thenReturn(routeDetails);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/routeId/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"stopId\":0,\"stopNumber\":0,\"hault\":\"hault\",\"distFromSource\":0,\"runningTime\":\"00:00:00\",\"routeId\":0}]");

    }

    @Test
    void testGetrouteDetailsByRouteId_RouteDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRouteDetailsService.getbyrouteId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/routeId/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testGetallstops() throws Exception {
        // Setup
        when(mockRouteDetailsService.findDistinctHaults()).thenReturn(List.of("value"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[\"value\"]");
    }

    @Test
    void testGetallstops_RouteDetailsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRouteDetailsService.findDistinctHaults()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/routeDetails/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
