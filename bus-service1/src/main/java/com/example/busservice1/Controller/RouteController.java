package com.example.busservice1.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.busservice1.Exception.RouteAlreadyExistException;
import com.example.busservice1.Exception.RouteNotFoundException;
import com.example.busservice1.Service.RouteService;
import com.example.busservice1.dto.RouteDto;
import com.example.busservice1.model.Route;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/buses")
public class RouteController {
	
	@Autowired
    private RouteService routeService;
	
	public RouteDto routeConvertToDto(Route route) {
		return new RouteDto(route.getRouteId(), route.getSource(),route.getDestination(), route.getTotalDistance(), route.getFarePerKm());
		}

		public Route routeConvertToEntity(RouteDto routeDto) {
		Route route = new Route(routeDto.getRouteId(),routeDto.getSource(),routeDto.getDestination(),routeDto.getTotalDistance(),routeDto.getFarePerKm());
		return route;
		}
		
		 @PostMapping("/route")
		    public ResponseEntity<RouteDto> create(@RequestBody RouteDto rDto){
		    Route route = routeConvertToEntity(rDto);
		    Optional<Route> check = routeService.getbyrouteId(rDto.getRouteId());
		    if(check.isPresent())
		    	throw new RouteAlreadyExistException();
		    Route createdRoute = routeService.save(route);
		    RouteDto dto = routeConvertToDto(createdRoute);
		    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		    }

		    @GetMapping("/route")
		    public ResponseEntity<List<RouteDto>> getAllRoute(){
		    List<Route> route = routeService.getall();
		    if(route.isEmpty())
		    throw new RouteNotFoundException();
		    List<RouteDto> routeDto=route.stream().map(r1 -> routeConvertToDto(r1)).collect(Collectors.toList());
		    return ResponseEntity.status(HttpStatus.OK).body(routeDto);
		    }

		    @GetMapping("/route/{routeId}")
		    public ResponseEntity<RouteDto> getroute(@PathVariable int routeId){


		    Optional<Route> routeOpt = routeService.getbyrouteId(routeId);
		    if(routeOpt.isEmpty())
		    	throw new RouteNotFoundException();
		    Route route = routeOpt.get();
		    RouteDto dto = routeConvertToDto(route);
		    return ResponseEntity.status(HttpStatus.OK).body(dto);
		    }
		    
		    @PutMapping("/route")
		    public ResponseEntity<RouteDto> updateroute(@RequestBody RouteDto route)
		    {
		    	Optional<Route> routeOpt = routeService.getbyrouteId(route.getRouteId());
		        if(routeOpt.isEmpty())
		        	throw new RouteNotFoundException();
				Route saved = routeConvertToEntity(route);
		        Route save = routeService.save(saved);
		        return ResponseEntity.status(HttpStatus.OK).body(routeConvertToDto(save));
		    	 
		    }
		   @GetMapping("/route/fare/{routeId}")
	      public ResponseEntity<Integer> getFare(@PathVariable int routeId)
		   {
               int a=routeService.getFare(routeId);
			   return  ResponseEntity.ok().body(a);
		   }


		    
	
	
	
	
}
