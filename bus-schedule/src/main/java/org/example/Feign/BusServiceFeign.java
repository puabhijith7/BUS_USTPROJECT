package org.example.Feign;

import org.example.Wrapper.BusDto;
import org.example.Wrapper.RouteDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="BUSSERVICE")
public interface BusServiceFeign {

    @GetMapping("api/v1/buses/routeDetails/r/{source}/{destination}")
    List<Integer> getrouteIdfromBusService(@PathVariable String source, @PathVariable String destination);

    @GetMapping("api/v1/buses/bus/{busId}")
    BusDto getBus(@PathVariable int busId);

    @GetMapping("api/v1/buses/routeDetails/r/{routeId}")
    List<Integer> searchforStopsbyRouteId(@PathVariable int routeId);

    @GetMapping("api/v1/buses/routeDetails/routeId/{routeId}")
    public List<RouteDetailsDto> getrouteDetailsByRouteId(@PathVariable int routeId);

    @GetMapping("api/v1/buses/route/fare/{routeId}")
    public Integer getFare(@PathVariable int routeId);
}
