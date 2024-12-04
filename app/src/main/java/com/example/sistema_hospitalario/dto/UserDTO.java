package com.example.sistema_hospitalario.dto;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String dni;
    private String userName;
    private String email;
    private String password;
    private Integer medical_license;

    public UserDTO(String id, String firstName, String lastName, String dni, String email, String userName, String password, int medicalLicense) {
    }

    public UserDTO(String firstName, String lastName, String dni, String email, String userName, String password, Integer medical_license) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.medical_license = medical_license;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMedical_license() {
        return medical_license;
    }

    public void setMedical_license(Integer medical_license) {
        this.medical_license = medical_license;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dni='" + dni + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", medical_license=" + medical_license +
                '}';
    }
}


