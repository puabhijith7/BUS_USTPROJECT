package org.example.Wrapper;


import org.example.Model.Schedule;

public class ScheduleBusWrapper {

    private BusDto b;
    private Schedule s;

    public ScheduleBusWrapper(BusDto b, Schedule s) {
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

    public Schedule getS() {
        return s;
    }

    public void setS(Schedule s) {
        this.s = s;
    }
}
