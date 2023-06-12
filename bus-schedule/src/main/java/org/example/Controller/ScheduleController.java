package org.example.Controller;


import org.example.Dto.ScheduleDto;
import org.example.Exception.ScheduleNotFoundException;
import org.example.Feign.BusServiceFeign;
import org.example.Model.Schedule;
import org.example.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public ScheduleDto convertToDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto(schedule.getScheduleId(),schedule.getDate(),schedule.getDepartureTime(),schedule.getArrivalTime(),schedule.getRouteId(),schedule.getBusId());
        return scheduleDto;
    }

    public Schedule convertToEntity(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule(scheduleDto.scheduleId(),scheduleDto.date(),scheduleDto.departureTime(),scheduleDto.arrivalTime(),scheduleDto.routeId(),scheduleDto.busId());
        return schedule ;
    }
//hai
    @Autowired
    private BusServiceFeign busServiceFeign;
    @GetMapping("/schedule/{date}/{source}/{destination}")
    public ResponseEntity<List<Schedule>> getbyDate(@PathVariable String date,@PathVariable String source,@PathVariable String destination)
    {
       LocalDate date1=LocalDate.parse(date);
       List<Schedule> s=scheduleService.getbydate(date1);
       List<Integer> r=new ArrayList<Integer>();
       List<Integer> id=new ArrayList<Integer>();
       List<Integer> bid=new ArrayList<Integer>();
       s.forEach(s1->r.add(s1.getRouteId()));
       List<Integer> r1= busServiceFeign.getrouteIdfromBusService(source,destination);
       if(r1.isEmpty())
       {
           throw new ScheduleNotFoundException();
       }
        for (Integer value : r) {
            for (Integer integer : r1) {
                if (value == integer) {
                    id.add(value);
                }
            }
        }

//comparing routeid with the routeid in that particular date and returning the
// busids

        for (Schedule schedule : s) {

            for (Integer integer : id) {
                if (schedule.getRouteId() == integer) {
                    bid.add(schedule.getBusId());
                }
            }

        }
 //comparing routeid with the routeid in that particular date and returning the
        // schedules
        List<Schedule> s1=new ArrayList<Schedule>();
        for(int i=0;i<s.size();i++)
        {

            for(int j=0;j<id.size();j++)
            {
                if(s.get(i).getRouteId()==id.get(j))
                {
                    s1.add(s.get(i));
                }
            }

        }
        if(s.isEmpty())
        {
            throw new ScheduleNotFoundException();
        }

        return ResponseEntity.ok().body(s1);
    }


}
