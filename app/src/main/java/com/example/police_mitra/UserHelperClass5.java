package com.example.police_mitra;

public class UserHelperClass5 {

    String crime_type, culprit_age, culprit_name, details,district,gender,
            station,username;
    long complaintNo,lati,longi,phone_no;

    public UserHelperClass5() {

    }

    public UserHelperClass5(long complaintNo, String crime_type, String culprit_age, String culprit_name, String details,
                            String district, String gender, long lati, long longi,long phone_no,String station,String username){

        this.complaintNo=complaintNo;
        this.crime_type=crime_type;
        this.culprit_age=culprit_age;
        this.details=details;
        this.culprit_name=culprit_name;
        this.district=district;
        this.lati=lati;
        this.longi=longi;
        this.phone_no=phone_no;
        this.station=station;
        this.username=username;
        this.gender=gender;

    }

    public long getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(long complaintNo) {
        this.complaintNo = complaintNo;
    }

    public String getCrime_type() {
        return crime_type;
    }

    public void setCrime_type(String crime_type) {
        this.crime_type = crime_type;
    }

    public String getCulprit_age() {
        return culprit_age;
    }

    public void setCulprit_age(String culprit_age) {
        this.culprit_age = culprit_age;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getLati() {
        return lati;
    }

    public void setLati(long lati) {
        this.lati = lati;
    }

    public long getLongi() {
        return longi;
    }

    public void setLongi(long longi) {
        this.longi = longi;
    }

    public long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(long phone_no) {
        this.phone_no = phone_no;
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
}
