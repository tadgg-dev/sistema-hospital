package com.example.sistema_hospitalario.dao.listeners;

import com.example.sistema_hospitalario.dto.PatientDTO;

import java.util.List;

public interface OnPatientsReceivedListener {
    void onPatientsReceived(List<PatientDTO> patients);
    void onError(Exception e);
}
