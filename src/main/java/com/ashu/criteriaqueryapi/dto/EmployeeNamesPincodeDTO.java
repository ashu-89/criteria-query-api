package com.ashu.criteriaqueryapi.dto;

public class EmployeeNamesPincodeDTO {

    private String fName;
    private String lName;
    private String pinCode;

    //Default constructor
    public EmployeeNamesPincodeDTO() {
    }

    public EmployeeNamesPincodeDTO(String fName, String lName, String pinCode) {
        this.fName = fName;
        this.lName = lName;
        this.pinCode = pinCode;
    }


    //Getters and setters

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}

