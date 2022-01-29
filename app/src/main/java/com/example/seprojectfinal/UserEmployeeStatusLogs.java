package com.example.seprojectfinal;

public class UserEmployeeStatusLogs {

    String email, date, time, status;

    UserEmployeeStatusLogs(){

    }

    UserEmployeeStatusLogs(String email, String date, String time, String status){
        this.email = email;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
