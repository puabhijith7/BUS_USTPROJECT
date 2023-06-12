package org.example.Feign;

import org.example.Wrapper.BusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="BUSSERVICE")
public interface BusServiceFeign {

    @GetMapping("api/v1/buses/routeDetails/r/{source}/{destination}")
    List<Integer> getrouteIdfromBusService(@PathVariable String source, @PathVariable String destination);

//    @GetMapping("/bus/{busId}")
//    ResponseEntity<BusDto> getBus(@PathVariable int busId);
}
