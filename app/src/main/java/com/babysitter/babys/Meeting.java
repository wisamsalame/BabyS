package com.babysitter.babys;

public class Meeting {

    String ID , IDCu , IDBS , nameCU , timeFrom , timeTO , dateOfMeeting , numberPhone ;
    int year , mounth, day , hour , minute ;

    public Meeting(String ID, String IDCu, String IDBS, String nameCU, String timeFrom, String timeTO, String dateOfMeeting, String numberPhone, int year, int mounth, int day, int hour, int minute) {
        this.ID = ID;
        this.IDCu = IDCu;
        this.IDBS = IDBS;
        this.nameCU = nameCU;
        this.timeFrom = timeFrom;
        this.timeTO = timeTO;
        this.dateOfMeeting = dateOfMeeting;
        this.numberPhone = numberPhone;
        this.year = year;
        this.mounth = mounth;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public Meeting() {
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getIDCu() {
        return IDCu;
    }

    public void setIDCu(String IDCu) {
        this.IDCu = IDCu;
    }

    public String getIDBS() {
        return IDBS;
    }

    public void setIDBS(String IDBS) {
        this.IDBS = IDBS;
    }

    public String getNameCU() {
        return nameCU;
    }

    public void setNameCU(String nameCU) {
        this.nameCU = nameCU;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTO() {
        return timeTO;
    }

    public void setTimeTO(String timeTO) {
        this.timeTO = timeTO;
    }

    public String getDateOfMeeting() {
        return dateOfMeeting;
    }

    public void setDateOfMeeting(String dateOfMeeting) {
        this.dateOfMeeting = dateOfMeeting;
    }
}
