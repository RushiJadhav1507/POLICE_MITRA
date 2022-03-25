package com.example.police_mitra;

public class CriminalDataHolder {

    public CriminalDataHolder(){}

    String ID,Name,Age,Booked_Under,Identity_Remarks,City;

    public CriminalDataHolder(String id,String name, String age, String booked_Under, String identity_Remarks, String city) {
        ID = id;
        Name = name;
        Age = age;
        Booked_Under = booked_Under;
        Identity_Remarks = identity_Remarks;
        City = city;

    }



    public String getID() {
        return ID;
    }

    public void setID(String Id) {
        ID = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBooked_Under() {
        return Booked_Under;
    }

    public void setBooked_Under(String booked_Under) {
        Booked_Under = booked_Under;
    }

    public String getIdentity_Remarks() {
        return Identity_Remarks;
    }


    public void setIdentity_Remarks(String identity_Remarks) {
        Identity_Remarks = identity_Remarks;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}

