package com.example.seprojectfinal;

public class UserSignIn {

    String email, password, num;

    public UserSignIn(){

    }

    public UserSignIn(String email, String password, String num){
        this.email = email;
        this.password = password;
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
