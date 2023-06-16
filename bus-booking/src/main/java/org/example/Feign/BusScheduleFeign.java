package org.example.Feign;

import org.example.Dto.ScheduleDto;
import org.example.Dto.SetSeatStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="BUSSCHEDULE")
public interface BusScheduleFeign {


    @GetMapping("api/v1/schedules/schedule/id/{scheduleId}")
    public ScheduleDto getSchedle(@PathVariable int scheduleId);

    @PostMapping("api/v1/schedules/seat/status")
    public void setseatstatus(@RequestBody SetSeatStatus sss);
}
