package com.example.busservice1.Controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.busservice1.Exception.BusAlreadyException;
import com.example.busservice1.Exception.BusNotFoundException;
import com.example.busservice1.Service.BusService;
import com.example.busservice1.dto.BusDto;
import com.example.busservice1.model.Bus;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/buses")
public class BusController {

    //hi
	
    @Autowired
    private BusService busService;

    public BusDto busConvertToDto(Bus bus) {
    	BusDto busDto = new BusDto(bus.getBusId(),bus.getBusNo(),bus.getRegNo(),bus.getEngineNo(), bus.getBusType(),bus.getBusName(), bus.getTotalSeats());
    	return busDto;
    	}
    
    public Bus busConvertToEntity(BusDto bus) {
    	Bus bus1 = new Bus(bus.getBusId(),bus.getBusNo(),bus.getRegNo(),bus.getEngineNo(), bus.getBusType(),bus.getBusName(), bus.getTotalSeats());
    	return bus1 ;
    	}
    
    @PostMapping("/bus")
    public ResponseEntity<BusDto> create(@RequestBody BusDto busDto){
    Bus bus = busConvertToEntity(busDto);
    Optional<Bus> check = busService.getbybusId(bus.getBusId());
    if(check.isPresent()) {
    	throw new BusAlreadyException();
    }
    Bus createdBus = busService.save(bus);
    BusDto dto = busConvertToDto(createdBus);
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/bus")
    public ResponseEntity<List<BusDto>> getAllBus(){
    List<Bus> bus = busService.getall();
    if(bus.isEmpty())
     throw new BusNotFoundException();
    List<BusDto> busDto=bus.stream().map(bus1 -> busConvertToDto(bus1)).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(busDto);
    }

    @GetMapping("/bus/{busId}")
    public BusDto getBus(@PathVariable int busId){


    Optional<Bus> busOpt = busService.getbybusId(busId);
    if(busOpt.isEmpty())
    	throw new BusNotFoundException();
    Bus bus = busOpt.get();
    BusDto dto = busConvertToDto(bus);
    return dto;
    }
    


//    @PostMapping("/bus")
//    public ResponseEntity<Bus> create(@RequestBody Bus bus){
//    	
//    	busService.save(bus);
//        return ResponseEntity.status(HttpStatus.CREATED).body(bus);
//    }
//    @GetMapping("/bus")
//    public List<Bus> getall()
//    {
//    	 return busService.getall();
//    }
//    @GetMapping("/bus/{busId}")
//    public ResponseEntity<Optional<Bus>> getbybusId(@PathVariable int busId)
//    {
//    	Optional<Bus> b=busService.getbybusId(busId);
//    	 if(b.isPresent())
//    	 {
//    		 return ResponseEntity.ok().body(b);
//    	 }
//    	 else
//    	 {
//    		 return ResponseEntity.notFound().build();
//    	 }
//    }
    
    
    @PutMapping("/bus")
    public ResponseEntity<?> updatebus(@RequestBody BusDto bus)
    {
    	Optional<Bus> busOpt = busService.getbybusId(bus.getBusId());
        if(busOpt.isEmpty())
        	throw new BusNotFoundException();
        Bus savedbus = busConvertToEntity(bus);
        busService.save(savedbus);
        return ResponseEntity.ok().build();
    	 
    }
    @DeleteMapping("/bus/{busId}")
    public ResponseEntity<?> deletebus(@PathVariable int busId)
    {
    	Optional<Bus> b=busService.getbybusId(busId);
    	 if(b.isPresent())
    	 {
    		 busService.delete(busId);
    		 return ResponseEntity.ok().build();
    	 }
    	 else
    	 {
    		 throw new BusNotFoundException();
    	 }
    }
    
    
    
    
    
    
    
    
    
    

  

}
