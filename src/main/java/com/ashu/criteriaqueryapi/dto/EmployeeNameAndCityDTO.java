package com.ashu.criteriaqueryapi.dto;

public class EmployeeNameAndCityDTO {

    private String fName;
    private String city;

    //Default constructor


    public EmployeeNameAndCityDTO() {
    }

    //All args constructor
    public EmployeeNameAndCityDTO(String fName, String city) {
        this.fName = fName;
        this.city = city;
    }

    //getters and setters
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
