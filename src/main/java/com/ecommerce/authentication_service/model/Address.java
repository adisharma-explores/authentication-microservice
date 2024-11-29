package com.ecommerce.authentication_service.model;


import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String streetName;
    private String cityName;
    private int pinCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public Address(int id, String streetName, String cityName, int pinCode) {
        super();
        this.id = id;
        this.streetName = streetName;
        this.cityName = cityName;
        this.pinCode = pinCode;
    }

    public Address() {
        super();
        // TODO Auto-generated constructor stub
    }

}
