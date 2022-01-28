package com.example.seprojectfinal;

public class UserView {
    String name, age, temperature;

    public UserView(){

    }

    public UserView(String name, String age, String temperature){
        this.name = name;
        this.age = age;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
