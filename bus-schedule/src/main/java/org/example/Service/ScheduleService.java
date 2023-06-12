package org.example.Service;

import org.example.Exception.ScheduleNotFoundException;
import org.example.Feign.BusServiceFeign;
import org.example.Model.Schedule;
import org.example.Repo.ScheduleRepo;
import org.example.Wrapper.ScheduleBusWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private BusServiceFeign busServiceFeign;
    public List<Schedule> getbydate(LocalDate date, String source, String destination) {

        List<Schedule> s= scheduleRepo.findByDate(date);
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
        List<ScheduleBusWrapper> ss=new ArrayList<>();



        for (Schedule schedule : s) {
            int schRoueId = schedule.getRouteId();
                if (id.contains(schRoueId)) {
                    bid.add(schedule.getBusId());
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
        if(s1.isEmpty())
        {
            throw new ScheduleNotFoundException();
        }

        return s1;
    }
    }

