package com.example.ifound.model;

public class Lostitem
{

    private String title;
    private String discriotion;
    private String status;
    private String time;
    private String location;

    public Lostitem(String title, String discriotion, String status, String time, String location, String key) {
        this.title = title;
        this.discriotion = discriotion;
        this.status = status;
        this.time = time;
        this.location = location;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

    public Lostitem(String title, String discription, String status, String location, String time) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscriotion() {
        return discriotion;
    }

    public void setDiscriotion(String discriotion) {
        this.discriotion = discriotion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



}
