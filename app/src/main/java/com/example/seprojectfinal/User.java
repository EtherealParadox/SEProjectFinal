package com.example.seprojectfinal;

public class User {

    String temperature, name, email, address, age, gender, symptoms, contact, destination;

    public User() {

    }

    public User(String temperature, String name, String email, String address, String age, String gender, String symptoms, String contact, String destination) {
        this.temperature = temperature;
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.symptoms = symptoms;
        this.contact = contact;
        this.destination = destination;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
