package com.babysitter.babys;

public class feedbackfromCu {
    String IDFB ,IDBS , IDCU , nameCU ;
    int year , mounth, day , hour , minute ;


    public feedbackfromCu(String IDFB, String IDBS, String IDCU, String nameCU, int year, int mounth, int day, int hour, int minute) {
        this.IDFB = IDFB;
        this.IDBS = IDBS;
        this.IDCU = IDCU;
        this.nameCU = nameCU;
        this.year = year;
        this.mounth = mounth;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

    }




    public String getIDFB() {
        return IDFB;
    }

    public void setIDFB(String IDFB) {
        this.IDFB = IDFB;
    }

    public feedbackfromCu() {
    }

    public String getIDBS() {
        return IDBS;
    }

    public void setIDBS(String IDBS) {
        this.IDBS = IDBS;
    }

    public String getIDCU() {
        return IDCU;
    }

    public void setIDCU(String IDCU) {
        this.IDCU = IDCU;
    }

    public String getNameCU() {
        return nameCU;
    }

    public void setNameCU(String nameCU) {
        this.nameCU = nameCU;
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


}
