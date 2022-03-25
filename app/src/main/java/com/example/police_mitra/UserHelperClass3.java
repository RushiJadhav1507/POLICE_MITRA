package com.example.police_mitra;

public class UserHelperClass3 {

    String name,email,address,pin,mob,adhar,DOB,gender;

    public UserHelperClass3() {

    }

    public UserHelperClass3(String name, String email, String address, String pin, String mob, String adhar, String DOB, String gen) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.pin = pin;
        this.mob = mob;
        this.adhar = adhar;
        this.DOB = DOB;
        this.gender=gen;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getAdhar() {
        return adhar;
    }

    public void setAdhar(String adhar) {
        this.adhar = adhar;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGd() {
        return gender;
    }

    public void setGd(String gd) {
        this.gender = gd;
    }

}
