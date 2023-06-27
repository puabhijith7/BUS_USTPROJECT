package org.example.Dto;

import java.time.LocalTime;

public class fareRunningtimeDto {

    private float fare;
    private LocalTime runningTime;
    public fareRunningtimeDto(){}
    public fareRunningtimeDto(float fare, LocalTime runningTime) {
        this.fare = fare;
        this.runningTime = runningTime;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public LocalTime getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(LocalTime runningTime) {
        this.runningTime = runningTime;
    }
}
