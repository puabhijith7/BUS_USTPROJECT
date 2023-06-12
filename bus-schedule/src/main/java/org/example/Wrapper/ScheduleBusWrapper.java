package org.example.Wrapper;


import org.example.Model.Schedule;

import java.util.List;

public class ScheduleBusWrapper {

    private BusDto b;
    private List<Schedule> s;

    public ScheduleBusWrapper(BusDto b, List<Schedule> s) {
        this.b = b;
        this.s = s;
    }
    public ScheduleBusWrapper(){}

    public BusDto getB() {
        return b;
    }

    public void setB(BusDto b) {
        this.b = b;
    }

    public List<Schedule> getS() {
        return s;
    }

    public void setS(List<Schedule> s) {
        this.s = s;

    }
}
