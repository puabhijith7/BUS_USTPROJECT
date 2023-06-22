package com.example.busservice1.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.busservice1.Exception.RouteDetailsAlreadyExistException;
import com.example.busservice1.Exception.RouteDetialsNotFoundException;
import com.example.busservice1.Service.RouteDetailsService;
import com.example.busservice1.dto.RouteDetailsDto;
import com.example.busservice1.model.RouteDetails;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/buses")
public class RouteDetailsController {
	 @Autowired
	    private RouteDetailsService routeDetailsService;
	 
	 public RouteDetailsDto routedetailsConvertToDto(RouteDetails rd) {
		 RouteDetailsDto rdDto = new RouteDetailsDto(rd.getStopId(),rd.getStopNumber(),rd.getHault(),rd.getDistFromSource(),rd.getRunningTime(), rd.getRouteId());
	    	return rdDto;
	    	}
	    
	 public RouteDetails routedetailsConvertToEntity(RouteDetailsDto routeDetailsDto) {
		 RouteDetails routeDetails = new RouteDetails(routeDetailsDto.getStopId(),routeDetailsDto.getStopNumber(),routeDetailsDto.getHault(),routeDetailsDto.getDistFromSource(),routeDetailsDto.getRunningTime(),routeDetailsDto.getRouteId());
		 return routeDetails;
		 }
	 
	  @GetMapping("/routeDetails/r/{source}/{destination}")
	    public ResponseEntity<List<Integer>> findDistinctRouteIdsByHault(@PathVariable String source,@PathVariable String destination)
	    {
	    	List<Integer> list=routeDetailsService.findDistinctRouteIdsByHault(source,destination);
//
	    	if(list.isEmpty())
				throw new RouteDetialsNotFoundException();
			return  ResponseEntity.ok().body(list);
	    }


	    
 
	    @PostMapping("/routeDetails")
	    public ResponseEntity<RouteDetailsDto> create(@RequestBody RouteDetailsDto rDto){
	    RouteDetails routedetails = routedetailsConvertToEntity(rDto);
	    Optional<RouteDetails> check = routeDetailsService.getbystopId(rDto.getStopId());
	    if(check.isPresent())
	    	throw new RouteDetailsAlreadyExistException();
	    RouteDetails createdRoute = routeDetailsService.save(routedetails);
	    RouteDetailsDto dto = routedetailsConvertToDto(createdRoute);
	    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	    }

	    @GetMapping("/routeDetails")
	    public ResponseEntity<List<RouteDetailsDto>> getAllRouteDetails(){
	    List<RouteDetails> routedetails = routeDetailsService.getall();
	    if(routedetails.isEmpty())
	     throw new RouteDetialsNotFoundException();
	    List<RouteDetailsDto> rdDto=routedetails.stream().map(r1 -> routedetailsConvertToDto(r1)).collect(Collectors.toList());
	    return ResponseEntity.status(HttpStatus.OK).body(rdDto);
	    }

	    @GetMapping("/routeDetails/{stopId}")
	    public ResponseEntity<RouteDetailsDto> getrouteDetails(@PathVariable int stopId){


	    Optional<RouteDetails> routeOpt = routeDetailsService.getbystopId(stopId);
	    if(routeOpt.isEmpty())
	    	throw new RouteDetialsNotFoundException();
	    RouteDetails route = routeOpt.get();
	    RouteDetailsDto dto = routedetailsConvertToDto(route);
	    return ResponseEntity.status(HttpStatus.OK).body(dto);
	    }
	    
	    @PutMapping("/routeDetails")
	    public ResponseEntity<RouteDetailsDto> updateroute(@RequestBody RouteDetailsDto route)
	    {
	    	Optional<RouteDetails> routeOpt = routeDetailsService.getbystopId(route.getStopId());
	        if(routeOpt.isEmpty())
	        	throw new RouteDetialsNotFoundException();
	        RouteDetails saved = routedetailsConvertToEntity(route);
	        RouteDetails save = routeDetailsService.save(saved);
	        return ResponseEntity.status(HttpStatus.OK).body(routedetailsConvertToDto(save));
	    	 
	    }
	    @DeleteMapping("/routeDetails/{stopId}")
	    public ResponseEntity<?> deleteroute(@PathVariable int stopId)
	    {
	    	Optional<RouteDetails> b=routeDetailsService.getbystopId(stopId);
	    	 if(b.isPresent())
	    	 {
	    		 routeDetailsService.delete(stopId);
	    		 return ResponseEntity.ok().build();
	    	 }
	    	 else
	    	 {
	    		 throw new RouteDetialsNotFoundException();
	    	 }
	    }
	    





	  @GetMapping("/routeDetails/r/{routeId}")
	 public ResponseEntity<List<Integer>> searchforStopsbyRouteId(@PathVariable int routeId)
	  {

		  List<Integer> list=routeDetailsService.searchforStopsbyRouteId(routeId);
		  return ResponseEntity.ok().body(list);
	  }

	@GetMapping("/routeDetails/routeId/{routeId}")
	public ResponseEntity<List<RouteDetailsDto>> getrouteDetailsByRouteId(@PathVariable int routeId){


		List<RouteDetails> routeOpt = routeDetailsService.getbyrouteId(routeId);
		if(routeOpt.isEmpty())
			throw new RouteDetialsNotFoundException();
		List<RouteDetailsDto> rdDto=routeOpt.stream().map(r1 -> routedetailsConvertToDto(r1)).collect(Collectors.toList());
		return ResponseEntity.ok().body(rdDto);
	}

	@GetMapping("/routeDetails/all")
	public ResponseEntity<List<String>> getallstops()
	{
		return ResponseEntity.ok().body(routeDetailsService.findDistinctHaults());
	}









}
