package com.vle.ventus;

// custom class for rooms
public class Room {
    // all temperatures are stored as celsius
    private String Name;
    private Integer CurrentTemp;
    private Integer CurrentHumidity;
    private Integer BatteryPercent;
    public Integer TargetTemp;

    public Room() {

    }

    public Room(String name, Integer currentTemp, Integer currentHumidity, Integer targetTemp, Integer batteryPercent) {
        Name = name;
        CurrentTemp = currentTemp;
        CurrentHumidity = currentHumidity;
        TargetTemp = targetTemp;
        BatteryPercent = batteryPercent;

    }

    public String getName() {
        return Name;
    }

    public Integer getCurrentTemp() {
        return CurrentTemp;
    }

    public Integer getCurrentHumidity() {
        return CurrentHumidity;
    }

    public Integer getTargetTemp() {
        return TargetTemp;
    }

    public Integer getBatteryPercent() { return BatteryPercent; }

    public void setName(String name) {
        Name = name;
    }

    public void setCurrentTemp(Integer currentTemp) { CurrentTemp = currentTemp; }

    public void setCurrentHumidity(Integer currentHumidity) {
        CurrentHumidity = currentHumidity;
    }

    public void setTargetTemp(Integer targetTemp) {
        TargetTemp = targetTemp;
    }

    public void setBatteryPercent(Integer batteryPercent) {
        BatteryPercent = batteryPercent;
    }

}
