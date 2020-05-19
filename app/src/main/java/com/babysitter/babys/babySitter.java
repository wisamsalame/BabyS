package com.babysitter.babys;

import java.io.Serializable;

public class babySitter implements Serializable {

    private String name;
    private String password;
    private String email ;
    private String data;
    private String phone_num;
    private boolean experince;
    private String active_limitations;
    private boolean ifHaveCar ;
    private String age;
    private String city;
    private double salary ;
    private double latitude ;
    private double longitude ;
    private boolean ifhisMale;
    private String address;
    private String imageURL;




    public babySitter(String name, String password, String email, String data, String phone_num, boolean experince, String active_limitations, boolean ifHaveCar, String age, String city, double salary, double latitude, double longitude, boolean ifhisMale, String address, String imageURL) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.data = data;
        this.phone_num = phone_num;
        this.experince = experince;
        this.active_limitations = active_limitations;
        this.ifHaveCar = ifHaveCar;
        this.age = age;
        this.city = city;
        this.salary = salary;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ifhisMale = ifhisMale;
        this.address = address;
        this.imageURL = imageURL;
    }

    public boolean isIfhisMale() {
        return ifhisMale;
    }

    public void setIfhisMale(boolean ifhisMale) {
        this.ifhisMale = ifhisMale;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public babySitter() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public boolean isExperince() {
        return experince;
    }

    public void setExperince(boolean experince) {
        this.experince = experince;
    }

    public String getActive_limitations() {
        return active_limitations;
    }

    public void setActive_limitations(String active_limitations) {
        this.active_limitations = active_limitations;
    }

    public boolean isIfHaveCar() {
        return ifHaveCar;
    }

    public void setIfHaveCar(boolean ifHaveCar) {
        this.ifHaveCar = ifHaveCar;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}


