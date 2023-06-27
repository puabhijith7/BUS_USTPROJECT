package org.example.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.example.Dto.SetSeatStatus;
//import org.example.Exception.ScheduleNotFoundException;
import org.example.Dto.fareRunningtimeDto;
import org.example.Feign.BusServiceFeign;
import org.example.Model.Schedule;
import org.example.Model.Seat;
import org.example.Repo.ScheduleRepo;
import org.example.Repo.SeatRepo;
import org.example.Wrapper.BusDto;
import org.example.Wrapper.RouteDetailsDto;
import org.example.exceptions.ScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
               seat.setScheduleId(schedule.getScheduleId());
               seat.setfHault(fhault);
               seat.settHault(thault);
               seat.setStatus(0);
               seat.setSeatNo(currSeat);
               seatList.add(seat);
           }
        }
        seatRepo.saveAll(seatList);
    }

    public List<fareRunningtimeDto> getFare(LocalDate date, String source, String destination) {
        List<Schedule> scheuleList = scheduleRepo.findByDate(date);
        List<Integer> r = new ArrayList<Integer>();
        List<Integer> routeid = new ArrayList<Integer>();
        List<Integer> busid = new ArrayList<Integer>();
        scheuleList.forEach(s1 -> r.add(s1.getRouteId()));
        List<Integer> routeIdfrombusfeign = busServiceFeign.getrouteIdfromBusService(source, destination);
       // System.out.println(routeIdfrombusfeign);
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
        List<fareRunningtimeDto> f=new ArrayList<>();

        LocalTime t = null;
        int sourceDist=0,destinationDist=0,farePerKm=0;
        List<RouteDetailsDto> rdto=new ArrayList<>();
        for(int i=0;i<routeid.size();i++)
        {
            fareRunningtimeDto f1 =new fareRunningtimeDto();
            rdto=busServiceFeign.getrouteDetailsByRouteId(routeid.get(i));
            farePerKm=busServiceFeign.getFare(routeid.get(i));
         //   System.out.println(farePerKm);
            for(int j=0;j<rdto.size();j++)
            {
                if(Objects.equals(rdto.get(j).getHault(), source))
                {
                    sourceDist=rdto.get(j).getDistFromSource();
                    System.out.println(sourceDist);

                }
                if(Objects.equals(rdto.get(j).getHault(), destination))
                {

                    destinationDist=rdto.get(j).getDistFromSource();
                    t=rdto.get(j).getRunningTime();
                    System.out.println(destinationDist);
                }
            }
           // System.out.println((destinationDist-sourceDist)*farePerKm);
           // f.add((float) ((destinationDist-sourceDist)*farePerKm));

            f1.setFare((float) ((destinationDist-sourceDist)*farePerKm));
            f1.setRunningTime(t);
            System.out.println(f1.getFare());
            System.out.println(f1.getRunningTime());
            f.add(f1);

        }
        return f;

    }

    public Optional<Schedule> getbyScheduleId(int scheduleId)
    {
        return scheduleRepo.findById(scheduleId);
    }

    public List<Seat> getseatsbyScheduleId(int id)
    {
        return seatRepo.findByScheduleId(id);
    }



    public Seat getseatstatus(SetSeatStatus sss) {
        List<Seat> seatsByFhault = seatRepo.findByFhault(sss.fhault());
        List<Seat> seatsByScheduleAndSeatNo = seatRepo.findByScheduleIdAndSeatNo(sss.scheduleId(), sss.seatNo());

        for (Seat seatByfhault : seatsByFhault) {
            for (Seat seat : seatsByScheduleAndSeatNo) {
                if (seatByfhault.equals(seat)) {
                    return seatByfhault; // Return the first common seat found
                }
            }
        }
        return null; // No common seat found
    }

    public Seat getseatstatus1(SetSeatStatus sss) {
        List<Seat> seatsByThault = seatRepo.findByThault(sss.thault());
        List<Seat> seatsByScheduleAndSeatNo = seatRepo.findByScheduleIdAndSeatNo(sss.scheduleId(), sss.seatNo());

        for (Seat seatBythault : seatsByThault) {
            for (Seat seat : seatsByScheduleAndSeatNo) {
                if (seatBythault.equals(seat)) {
                    return seatBythault; // Return the first common seat found
                }
            }
        }
        return null; // No common seat found
    }

    @Transactional
    public void setstatus(Seat s1, Seat s2) {
         seatRepo.setstatus(s1.getSeatId(),s2.getSeatId());
    }
    public List<Schedule> getall() {
        return scheduleRepo.findAll();
    }
}





