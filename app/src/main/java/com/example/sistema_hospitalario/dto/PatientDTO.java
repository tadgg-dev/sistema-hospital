package com.example.sistema_hospitalario.dto;

public class PatientDTO {

    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private Integer user_medical_code;
    private String dateOfBirth;
    private String gender;
    private String phone;
    private String address;
    private String doctor_email;

    public PatientDTO(String firstName, String lastName, String dni, String email, Integer user_medical_code, String dateOfBirth, String gender, String phone, String address, String doctor_email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.user_medical_code = user_medical_code;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.doctor_email = doctor_email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUser_medical_code() {
        return user_medical_code;
    }

    public void setUser_medical_code(Integer user_medical_code) {
        this.user_medical_code = user_medical_code;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }
}
