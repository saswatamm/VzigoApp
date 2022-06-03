package com.example.vzigo;

public class GetVisitor {
    public GetVisitor(String id, String name, String status, String image, String checkIn, String checkout, String dob, String gender, String phone, String emailId, String address, String city, String zipCode, String purpose, String personToVisit, String govtId, Boolean isCovidVaccinated, String digitalSignature) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
        this.checkIn = checkIn;
        this.checkout = checkout;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.emailId = emailId;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.purpose = purpose;
        this.personToVisit = personToVisit;
        this.govtId = govtId;
        this.isCovidVaccinated = isCovidVaccinated;
        this.digitalSignature = digitalSignature;
    }

    String id, name, status, image,checkIn,checkout,dob, gender, phone, emailId, address, city, zipCode, purpose, personToVisit, govtId;
    Boolean isCovidVaccinated;
    String digitalSignature;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPersonToVisit() {
        return personToVisit;
    }

    public void setPersonToVisit(String personToVisit) {
        this.personToVisit = personToVisit;
    }

    public String getGovtId() {
        return govtId;
    }

    public void setGovtId(String govtId) {
        this.govtId = govtId;
    }

    public Boolean getCovidVaccinated() {
        return isCovidVaccinated;
    }

    public void setCovidVaccinated(Boolean covidVaccinated) {
        isCovidVaccinated = covidVaccinated;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }


}
