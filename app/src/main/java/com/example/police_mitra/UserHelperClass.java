package com.example.police_mitra;

public class UserHelperClass {

    String username,name,email,aadhar,age,gender,phone,degn,asspolice,pass;

    public UserHelperClass(){

    }
    public UserHelperClass(String username,String name,String aadhar,String age,String gender,String email,String phone,String degn,String asspolice,String pass){

        this.username=username;
        this.name=name;
        this.email=email;
        this.aadhar=aadhar;
        this.age=age;
        this.gender=gender;
        this.phone=phone;
        this.degn=degn;
        this.asspolice=asspolice;
        this.pass=pass;

    }
    public String getUsername(){

        return username;

    }

    public void setUsername(String username){

        this.username=username;

    }

    public String getName(){

        return name;

    }

    public void setName(String name){

        this.name=name;

    }


    public String getEmail(){

        return email;

    }

    public void setEmail(String email){

        this.email=email;

    }

    public String getAadhar(){

        return aadhar;

    }

    public void setAadhar(String aadhar){

        this.aadhar=aadhar;

    }

    public String getAge(){

        return age;

    }

    public void setAge(String age){

        this.age=age;

    }

    public String getGender(){

        return gender;
    }

    public void setGender(){

        this.gender=gender;

    }

    public String getPhone(){

        return phone;

    }

    public void setPhone(){

        this.phone=phone;

    }

    public String getDegn(){

        return degn;

    }

    public void setDegn(){

        this.degn=degn;

    }

    public String getAsspolice(){

        return asspolice;

    }

    public void setAsspolice(){

        this.asspolice=asspolice;

    }

    public String getPass(){

        return pass;

    }

    public void setPass(){

        this.pass=pass;

    }

}
