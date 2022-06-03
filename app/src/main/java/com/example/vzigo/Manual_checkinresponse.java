package com.example.vzigo;

public class Manual_checkinresponse {
    private String purpose;
    private String status;
    private String visitorId;
    private String emailId;
    private String address;
    private String name;
    private String clientId;
    private String gender;
    private String digitalSignature;
    private boolean isCovidVaccinated;
    private String city;
    private String checkInTime;
    private String personToVisit;
    private String dateOfBirth;
    private String zipCode;
    private String checkInDate;
    private String phone;
    private String govtId;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public boolean isCovidVaccinated() {
        return isCovidVaccinated;
    }

    public void setCovidVaccinated(boolean covidVaccinated) {
        isCovidVaccinated = covidVaccinated;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getPersonToVisit() {
        return personToVisit;
    }

    public void setPersonToVisit(String personToVisit) {
        this.personToVisit = personToVisit;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Manual_checkinresponse(String purpose, String status, String visitorId, String emailId, String address, String name, String clientId, String gender, String digitalSignature, boolean isCovidVaccinated, String city, String checkInTime, String personToVisit, String dateOfBirth, String zipCode, String checkInDate, String phone, String govtId) {
        this.purpose = purpose;
        this.status = status;
        this.visitorId = visitorId;
        this.emailId = emailId;
        this.address = address;
        this.name = name;
        this.clientId = clientId;
        this.gender = gender;
        this.digitalSignature = digitalSignature;
        this.isCovidVaccinated = isCovidVaccinated;
        this.city = city;
        this.checkInTime = checkInTime;
        this.personToVisit = personToVisit;
        this.dateOfBirth = dateOfBirth;
        this.zipCode = zipCode;
        this.checkInDate = checkInDate;
        this.phone = phone;
        this.govtId = govtId;
    }
}
