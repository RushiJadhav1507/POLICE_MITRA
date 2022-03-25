package com.example.police_mitra;

public class DB_Register {

    String culprit_name;
    String culprit_age;
    String gender;
    long phone_no;
    String crime_type;
    String details;
    double lati, longi;
    String district;
    String station, username;
    int complaintNo;
    String status;

    public String getCulprit_age() {
        return culprit_age;
    }

    public DB_Register(){}

    public void setCulprit_age(String culprit_age) {
        this.culprit_age = culprit_age;
    }

    public DB_Register(String culprit_name, int complaintNo, String culprit_age,  String gender, long phone_no, String crime_type, String details, double lati, double longi, String district, String station, String username ) {
        this.culprit_name = culprit_name;
        this.culprit_age = culprit_age;
        this.gender = gender;
        this.phone_no = phone_no;
        this.crime_type = crime_type;
        this.details = details;
        this.lati = lati;
        this.longi = longi;
        this.district = district;
        this.station = station;
        this.username = username;
        this.complaintNo = complaintNo;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(int complaintNo) {
        this.complaintNo = complaintNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DB_Register(String culprit_name, String culprit_age, String gender, long phone_no, String crime_type, String details, double lati, double longi, String district, String station, String status, int complaintNo) {
        this.culprit_name = culprit_name;
        this.culprit_age = culprit_age;
        this.gender = gender;
        this.phone_no = phone_no;
        this.crime_type = crime_type;
        this.details = details;
        this.lati = lati;
        this.longi = longi;
        this.district = district;
        this.station = station;
        this.status = status;
        this.complaintNo = complaintNo;
    }

    public String getCulprit_name() {
        return culprit_name;
    }

    public void setCulprit_name(String culprit_name) {
        this.culprit_name = culprit_name;
    }



    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(long phone_no) {
        this.phone_no = phone_no;
    }

    public String getCrime_type() {
        return crime_type;
    }

    public void setCrime_type(String crime_type) {
        this.crime_type = crime_type;
    }

}
