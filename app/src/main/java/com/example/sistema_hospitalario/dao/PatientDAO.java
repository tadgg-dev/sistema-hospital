package com.example.sistema_hospitalario.dao;

import com.example.sistema_hospitalario.dao.listeners.OnPatientReceivedListener;
import com.example.sistema_hospitalario.dao.listeners.OnPatientsReceivedListener;
import com.example.sistema_hospitalario.dto.PatientDTO;

public interface PatientDAO {
    void createPatient(PatientDTO patient);
    void updatePatient(String email, PatientDTO patient);
    void deletePatient(String email);
    void getPatient(String email, OnPatientReceivedListener listener);
    void getAllPatientsByDoctorEmail(String email, OnPatientsReceivedListener listener);
}
