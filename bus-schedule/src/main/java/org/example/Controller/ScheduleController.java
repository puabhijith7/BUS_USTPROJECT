package org.example.Controller;


import org.example.Dto.ScheduleDto;
import org.example.Model.Schedule;
import org.example.Service.ScheduleService;
import org.example.Wrapper.BusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public ScheduleDto convertToDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto(schedule.getScheduleId(), schedule.getDate(), schedule.getDepartureTime(), schedule.getArrivalTime(), schedule.getRouteId(), schedule.getBusId());
        return scheduleDto;
    }

    public Schedule convertToEntity(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule(scheduleDto.scheduleId(), scheduleDto.date(), scheduleDto.departureTime(), scheduleDto.arrivalTime(), scheduleDto.routeId(), scheduleDto.busId());
        return schedule;
    }


    @GetMapping("/schedule/{date}/{source}/{destination}")
    public ResponseEntity<List<Schedule>> getbyDate(@PathVariable String date, @PathVariable String source, @PathVariable String destination) {
        LocalDate date1 = LocalDate.parse(date);
        return ResponseEntity.ok().body(scheduleService.getbydate(date1, source, destination));

    }
       //FOR FEIGN-BUS
    @GetMapping("/schedule/bus/{date}/{source}/{destination}")
    public ResponseEntity<List<BusDto>> getbyDateBus(@PathVariable String date, @PathVariable String source, @PathVariable String destination) {
        LocalDate date1 = LocalDate.parse(date);
        return ResponseEntity.ok().body(scheduleService.getbydateBus(date1, source, destination));

    }

    //for FARE
    @GetMapping("/schedule/fare/{date}/{source}/{destination}")
    public ResponseEntity<List<Float>> getFare(@PathVariable String date, @PathVariable String source, @PathVariable String destination) {
        LocalDate date1 = LocalDate.parse(date);
        return ResponseEntity.ok().body(scheduleService.getFare(date1, source, destination));
    }
    @PostMapping("/schedule/post")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDto scheduleDto){
        Schedule schedule = convertToEntity(scheduleDto);
        scheduleService.save(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/schedule/id/{scheduleId}")
    public ScheduleDto getSchedle(@PathVariable int scheduleId){


        Optional<Schedule> schOpt = scheduleService.getbyScheduleId(scheduleId);
//        if(schOpt.isEmpty())
//            throw new BusNotFoundException();
        Schedule schedule = schOpt.get();
        ScheduleDto dto = convertToDto(schedule);
        return dto;
    }
}
