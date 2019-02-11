package com.vle.ventus;

public class Device {

    private String Name;
    private Integer CurrentTemp;
    public Integer TargetTemp;
    private String Battery;

    public Device() {

    }

    public Device(String name, Integer currentTemp, Integer targetTemp, String battery) {
        Name = name;
        CurrentTemp = currentTemp;
        TargetTemp = targetTemp;
        Battery = battery;
    }

    public String getName() {
        return Name;
    }

    public Integer getCurrentTemp() {
        return CurrentTemp;
    }

    public Integer getTargetTemp() {
        return TargetTemp;
    }

    public String getBattery() {
        return Battery;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCurrentTemp(Integer currentTemp) {
        CurrentTemp = currentTemp;
    }

    public void setTargetTemp(Integer targetTemp) {
        TargetTemp = targetTemp;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }
}
