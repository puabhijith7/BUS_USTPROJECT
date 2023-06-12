package org.example.Service;

import org.example.Model.Schedule;
import org.example.Repo.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    public List<Schedule> getbydate(LocalDate date) {
       return scheduleRepo.findByDate(date);
    }
}
