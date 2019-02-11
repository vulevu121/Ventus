package com.vle.ventus;

public class Schedule {
    private String Name;
    private String TimeStart;
    private String TimeEnd;

    public Schedule() {

    }

    public Schedule(String name, String timeStart, String timeEnd) {
        Name = name;
        TimeStart = timeStart;
        TimeEnd = timeEnd;
    }


    public String getName() {
        return Name;
    }

    public String getTimeStart() {
        return TimeStart;
    }

    public String getTimeEnd() {
        return TimeEnd;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTimeStart(String timeStart) {
        TimeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        TimeEnd = timeEnd;
    }

}
