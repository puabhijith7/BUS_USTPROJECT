package org.example.Feign;

import org.example.Dto.ScheduleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="BUSSCHEDULE")
public interface BusScheduleFeign {


    @GetMapping("api/v1/schedules/schedule/id/{scheduleId}")
    public ScheduleDto getSchedle(@PathVariable int scheduleId);
}
