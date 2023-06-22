package com.example.busservice1.Controller;

import com.example.busservice1.Service.RouteService;
import com.example.busservice1.dto.RouteDto;
import com.example.busservice1.model.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService mockRouteService;
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
    void testCreate_ThrowsRouteAlreadyExistException() throws Exception {
        // Setup
        // Configure RouteService.getbyrouteId(...).
        final Optional<Route> route = Optional.of(new Route(0, "source", "destination", 0, 0));
        when(mockRouteService.getbyrouteId(0)).thenReturn(route);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/buses/route")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void testCreate_RouteServiceGetbyrouteIdReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteService.getbyrouteId(0)).thenReturn(Optional.empty());

        // Configure RouteService.save(...).
        final Route route = new Route(0, "source", "destination", 0, 0);
        when(mockRouteService.save(any(Route.class))).thenReturn(route);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/buses/route")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void testGetAllRoute() throws Exception {
        // Setup
        // Configure RouteService.getall(...).
        final List<Route> routes = List.of(new Route(0, "source", "destination", 0, 0));
        when(mockRouteService.getall()).thenReturn(routes);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/route")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"routeId\":0,\"totalDistance\":0,\"farePerKm\":0,\"source\":\"source\",\"destination\":\"destination\"}]");

    }

    @Test
    void testGetAllRoute_RouteServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRouteService.getall()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/route")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testGetroute() throws Exception {
        // Setup
        // Configure RouteService.getbyrouteId(...).
        final Optional<Route> route = Optional.of(new Route(0, "source", "destination", 0, 0));
        when(mockRouteService.getbyrouteId(0)).thenReturn(route);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/route/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"routeId\":0,\"totalDistance\":0,\"farePerKm\":0,\"source\":\"source\",\"destination\":\"destination\"}");

    }

    @Test
    void testGetroute_RouteServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteService.getbyrouteId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/route/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testUpdateroute() throws Exception {
        // Setup
        // Create a sample RouteDto object
        RouteDto routeDto = new RouteDto();
        routeDto.setRouteId(0);
        // Set other properties of routeDto

        // Configure RouteService.getbyrouteId(...)
        final Optional<Route> routeOpt = Optional.of(new Route());
        when(mockRouteService.getbyrouteId(0)).thenReturn(routeOpt);

        // Configure RouteService.save(...)
        final Route savedRoute = new Route();
        // Set properties of savedRoute based on the routeDto
        when(mockRouteService.save(any(Route.class))).thenReturn(savedRoute);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/buses/route")
                        .content(objectMapper.writeValueAsString(routeDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(routeDto));
    }


    @Test
    void testUpdateroute_RouteServiceGetbyrouteIdReturnsAbsent() throws Exception {
        // Setup
        when(mockRouteService.getbyrouteId(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/v1/buses/route")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
      //  assertThat(response.getContentAsString()).isEqualTo("not found");
    }

    @Test
    void testGetFare() throws Exception {
        // Setup
        when(mockRouteService.getFare(0)).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/buses/route/fare/{routeId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("0");
    }
}
