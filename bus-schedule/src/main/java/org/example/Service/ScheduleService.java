package org.example.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.Exception.ScheduleNotFoundException;
import org.example.Feign.BusServiceFeign;
import org.example.Model.Schedule;
import org.example.Model.Seat;
import org.example.Repo.ScheduleRepo;
import org.example.Repo.SeatRepo;
import org.example.Wrapper.BusDto;
import org.example.Wrapper.RouteDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private SeatRepo seatRepo;
    public void save(Schedule schedule) {
        String fhault ="",thault="";
        scheduleRepo.save(schedule);
        List<Seat> seatList=new ArrayList<>();
        BusDto b=busServiceFeign.getBus(schedule.getBusId());
        List<RouteDetailsDto> rdto=busServiceFeign.getrouteDetailsByRouteId(schedule.getRouteId());
        List<Integer> l=busServiceFeign.searchforStopsbyRouteId(schedule.getRouteId());
        for(int currSeat=1;currSeat<=b.getTotalSeats();currSeat++)
        {
           for(int j=l.get(0);j<l.get(1);j++)
           {
               for (RouteDetailsDto rd:rdto) {
                   if(rd.getStopNumber()==j)
                       fhault = rd.getHault();
                   if(rd.getStopNumber()==j+1)
                       thault = rd.getHault();
               }
               Seat seat=new Seat();
               System.out.println(l.get(0));
               System.out.println(l.get(1));
               seat.setSchedule_id(schedule.getScheduleId());
               seat.setfHault(fhault);
               seat.settHault(thault);
               seat.setStatus(0);
               seat.setSeat_no(currSeat);
               seatList.add(seat);
           }
        }
        seatRepo.saveAll(seatList);
    }

    public List<Float> getFare(LocalDate date, String source, String destination) {
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
        List<Float> f=new ArrayList<>();
        int sourceDist=0,destinationDist=0,farePerKm=0;
        List<RouteDetailsDto> rdto=new ArrayList<>();
        for(int i=0;i<routeid.size();i++)
        {
            rdto=busServiceFeign.getrouteDetailsByRouteId(routeid.get(i));
            farePerKm=busServiceFeign.getFare(routeid.get(i));
            for(int j=0;j<rdto.size();j++)
            {
                if(Objects.equals(rdto.get(j).getHault(), source))
                {
                    sourceDist=rdto.get(j).getDistFromSource();
                   // System.out.println(sourceDist);

                }
                if(Objects.equals(rdto.get(j).getHault(), destination))
                {

                    destinationDist=rdto.get(j).getDistFromSource();
                 //   System.out.println(destinationDist);
                }
            }
            f.add((float) ((destinationDist-sourceDist)*farePerKm));

        }
        return f;

    }
}

