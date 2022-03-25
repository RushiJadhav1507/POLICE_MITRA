package com.example.police_mitra;

public class UserHelperClass2 {

    String sub,pdate,desc,noti;

    UserHelperClass2(){


    }

    UserHelperClass2(String sub,String pdate,String desc,String noti){
        this.sub=sub;
        this.pdate=pdate;
        this.desc=desc;
        this.noti=noti;


    }

    public String getSub(){

        return sub;

    }
    public void setSub(String sub){

        this.sub=sub;

    }

    public String getPdate(){

        return pdate;

    }
    public void setPdate(String pdate){

        this.pdate=pdate;

    }

    public String getDesc(){

        return desc;

    }
    public void setDesc(String desc){

        this.desc=desc;

    }

    public String getNoti(){

        return noti;

    }
    public void setNoti(String noti){

        this.noti=noti;

    }


}
