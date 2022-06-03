package com.example.vzigo;

public class RegisterVisitors {
    private String name;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String emailId;
    private String address;
    private String city;
    private String zipCode;
    private String purpose;
    private String whomToVisit;
    private String govtId;
    private boolean isCovidVaccinated;
    private String digitalSignature;

    public RegisterVisitors(String name, String phone, String dateOfBirth, String gender, String emailId, String address, String city, String zipCode, String purpose, String whomToVisit, String govtId, boolean isCovidVaccinated, String digitalSignature) {
        this.name = name;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.emailId = emailId;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.purpose = purpose;
        this.whomToVisit = whomToVisit;
        this.govtId = govtId;
        this.isCovidVaccinated = isCovidVaccinated;
        this.digitalSignature = digitalSignature;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getwhomToVisit() {
        return whomToVisit;
    }

    public String getGovtId() {
        return govtId;
    }

    public boolean getIsCovidVaccinated() {
        return isCovidVaccinated;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setPersonToVisit(String whomToVisit) {
        this.whomToVisit = whomToVisit;
    }

    public void setGovtId(String govtId) {
        this.govtId = govtId;
    }

    public void setIsCovidVaccinated(boolean isCovidVaccinated) {
        this.isCovidVaccinated = isCovidVaccinated;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}
