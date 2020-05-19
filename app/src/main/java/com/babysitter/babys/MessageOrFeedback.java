package com.babysitter.babys;

public class MessageOrFeedback {
    String ID,IDCU ,IDBS,nameBS , message ;

    int year , mounth, day , hour , minute ;

    public MessageOrFeedback(String ID,String IDCU, String IDBS, String nameBS, String message, int year, int mounth, int day, int hour, int minute) {
        this.IDCU = IDCU;
        this.ID = ID;
        this.IDBS = IDBS;
        this.nameBS = nameBS;
        this.message = message;

        this.year = year;
        this.mounth = mounth;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMounth() {
        return mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getIDCU() {
        return IDCU;
    }

    public void setIDCU(String IDCU) {
        this.IDCU = IDCU;
    }


    public MessageOrFeedback() {
    }

    public String getIDBS() {
        return IDBS;
    }

    public void setIDBS(String IDBS) {
        this.IDBS = IDBS;
    }

    public String getNameBS() {
        return nameBS;
    }

    public void setNameBS(String nameBS) {
        this.nameBS = nameBS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
