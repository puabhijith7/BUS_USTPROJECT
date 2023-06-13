package org.example.Service;

import org.example.Exception.ScheduleNotFoundException;
import org.example.Feign.BusServiceFeign;
import org.example.Model.Schedule;
import org.example.Repo.ScheduleRepo;
import org.example.Wrapper.BusDto;
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

        List<Schedule> scheuleList= scheduleRepo.findByDate(date);
        List<Integer> r=new ArrayList<Integer>();
        List<Integer> routeid=new ArrayList<Integer>();
        List<Integer> busid=new ArrayList<Integer>();
        scheuleList.forEach(s1->r.add(s1.getRouteId()));
        List<Integer> routeIdfrombusfeign= busServiceFeign.getrouteIdfromBusService(source,destination);
        if(routeIdfrombusfeign.isEmpty())
        {
            throw new ScheduleNotFoundException();
        }
        for (Integer value : r) {
            for (Integer value1 : routeIdfrombusfeign) {
                if (value == value1) {
                    routeid.add(value);
                }
            }
        }
        //comparing routeid with the routeid in that particular date and returning the
        // schedules
        List<Schedule> s1=new ArrayList<Schedule>();
        for (Schedule schedule : scheuleList) {
            int schRoueId = schedule.getRouteId();
            if (routeid.contains(schRoueId)) {
                s1.add(schedule);
            }
        }
        if(s1.isEmpty())
        {
            throw new ScheduleNotFoundException();
        }

        return s1;
    }

    public List<BusDto> getbydateBus(LocalDate date, String source, String destination) {

        List<Schedule> scheuleList = scheduleRepo.findByDate(date);
        List<Integer> r = new ArrayList<Integer>();
        List<Integer> routeid = new ArrayList<Integer>();
        List<Integer> busid = new ArrayList<Integer>();
        scheuleList.forEach(s1 -> r.add(s1.getRouteId()));
        List<Integer> routeIdfrombusfeign = busServiceFeign.getrouteIdfromBusService(source, destination);
        if (routeIdfrombusfeign.isEmpty()) {
            throw new ScheduleNotFoundException();
        }
        for (Integer value : r) {
            for (Integer value1 : routeIdfrombusfeign) {
                if (value == value1) {
                    routeid.add(value);
                }
            }
        }

//comparing routeid with the routeid in that particular date and returning the
// busids


        for (Schedule schedule : scheuleList) {
            int schRoueId = schedule.getRouteId();
            if (routeid.contains(schRoueId)) {
                busid.add(schedule.getBusId());
            }
        }
        List<BusDto> busDtoList = new ArrayList<BusDto>();
        for (int i = 0; i < busid.size(); i++) {
            busDtoList.add(busServiceFeign.getBus(busid.get(i)));
        }
        return busDtoList;
    }

}

