package com.example.logindemo;

public class posts {
    public String date,postdescrip,time,fullname,uid;

    public posts(){ }
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
