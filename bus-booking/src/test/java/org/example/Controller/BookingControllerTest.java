package org.example.Controller;
import org.example.Dto.PassengerDto;
import org.example.Dto.RequestDto;
import org.example.Service.BookingService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService mockBookingService;

    @Test
    void testPostBooking() throws Exception {
// Setup
        when(mockBookingService.save(new RequestDto("source", "destination", 0, 0, 0, 0.0f, "date", List.of(0),
                List.of(new PassengerDto("name", "email", "mobileNo", 0, "gender"))))).thenReturn(0);

        String requestPayload = "{\"source\":\"source\",\"destination\":\"destination\",\"property1\":0,\"property2\":0,\"property3\":0,\"property4\":0.0,\"date\":\"date\",\"list1\":[0],\"passengers\":[{\"name\":\"name\",\"email\":\"email\",\"mobileNo\":\"mobileNo\",\"property\":0,\"gender\":\"gender\"}]}";

// Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/bookings/booking")
                        .content(requestPayload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

// Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("0", response.getContentAsString()); // Update the expected response body here
    }
}