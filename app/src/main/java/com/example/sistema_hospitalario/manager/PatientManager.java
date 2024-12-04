package com.example.sistema_hospitalario.manager;

import android.content.Context;

import com.example.sistema_hospitalario.dao.PatientDAO;
import com.example.sistema_hospitalario.dao.listeners.OnPatientReceivedListener;
import com.example.sistema_hospitalario.dao.listeners.OnPatientsReceivedListener;
import com.example.sistema_hospitalario.dao.sqliteDAO.SqlitePatientDAO;
import com.example.sistema_hospitalario.dto.PatientDTO;


import java.util.concurrent.ExecutorService;

public class PatientManager implements PatientDAO {
    private final ExecutorService executorService;
    private SqlitePatientDAO sqlitePatientDAO;

    public PatientManager(ExecutorService executorService) { this.executorService = executorService;}

    public void setContext(Context context) { sqlitePatientDAO = new SqlitePatientDAO(context);}

    public void createPatient(PatientDTO patient) {
        executorService.execute(() -> sqlitePatientDAO.createPatient(patient));
    }

    public void updatePatient(String email, PatientDTO patient) {
        executorService.execute(() -> sqlitePatientDAO.updatePatient(email, patient));
    }

    public void deletePatient(String email) {
        executorService.execute(() -> sqlitePatientDAO.deletePatient(email));
    }

    public void getPatient(String email, OnPatientReceivedListener listener) {
        executorService.execute(() -> sqlitePatientDAO.getPatient(email, listener));
    }

    public void getAllPatientsByDoctorEmail(String email, OnPatientsReceivedListener listener) {
        executorService.execute(() -> sqlitePatientDAO.getAllPatientsByDoctorEmail(email, listener));
    }
}
