package sample.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Calendar{
        private int calendar_id;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private String serviceName;
        private String employeeName;
        private String placementName;

    public Calendar() {
    }

    public Calendar(LocalDate date, LocalTime startTime, String serviceName, String employeeName, String placementName) {
        this.date = date;
        this.startTime = startTime;
        this.serviceName = serviceName;
        this.employeeName = employeeName;
        this.placementName = placementName;
    }

    public int getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(int rowNumber) {
        this.calendar_id = rowNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPlacementName() {
        return placementName;
    }

    public void setPlacementName(String placementName) {
        this.placementName = placementName;
    }


}
