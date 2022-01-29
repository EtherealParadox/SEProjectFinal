package com.example.seprojectfinal;

public class UserView {
    String name, date, time,temperature;

    public UserView(){

    }

    public UserView(String name, String date, String time, String temperature){
        this.name = name;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
