package com.example.seprojectfinal;

public class UserSignUp {

    String fname, lname, email, uname, pass;

    public UserSignUp(){

    }

    public UserSignUp(String fname, String lname, String email, String uname, String pass){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.uname = uname;
        this.pass = pass;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
