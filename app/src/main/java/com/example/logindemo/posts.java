package com.example.logindemo;

public class posts {
    public String date,postdescrip,time,fullname,uid;

    public posts(){}

    public void setDate(String date) {
        this.date = date;
    }

    public void setPostdescrip(String postdescrip) {
        this.postdescrip = postdescrip;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public posts(String date, String postdescrip, String time, String fullname, String uid) {
        this.date = date;
        this.postdescrip = postdescrip;
        this.time = time;
        this.fullname = fullname;
        this.uid = uid;
        //  this.profileimage = profileimage;
    }


    public String getDate() { return date; }



    public String getPostdescrip() {
        return postdescrip;
    }


    public String getTime() {
        return time;
    }



    public String getFullname() {
        return fullname;
    }








}
