package com.vle.ventus;

public class Room {

    private String Name;
    private Integer CurrentTemp;
    private Integer CurrentHumidity;
    public Integer TargetTemp;
    private Integer BatteryPercent;

//    private int Thumbnail;

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

//    public int getThumbnail() {
//        return Thumbnail;
//    }

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

//    public void setThumbnail(int thumbnail) {
//        Thumbnail = thumbnail;
//    }
}
