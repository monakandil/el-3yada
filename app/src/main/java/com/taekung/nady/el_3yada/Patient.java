package com.taekung.nady.el_3yada;

/**
 * Created by Taekunger on 5/20/2016.
 */
public class Patient{

   private int id;
    private String name;
    private String email;
    private String tel;
    private String date_of_arrival;
    private String time_of_arrival;
    private String disease;
    private String medication;
    private double cost;

    public Patient(int id, String name, String email, String tel, String date_of_arrival, String time_of_arrival, String disease, String medication, double cost) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.date_of_arrival = date_of_arrival;
        this.time_of_arrival = time_of_arrival;
        this.disease = disease;
        this.medication = medication;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate_of_arrival() {
        return date_of_arrival;
    }

    public void setDate_of_arrival(String date_of_arrival) {
        this.date_of_arrival = date_of_arrival;
    }

    public String getTime_of_arrival() {
        return time_of_arrival;
    }

    public void setTime_of_arrival(String time_of_arrival) {
        this.time_of_arrival = time_of_arrival;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
