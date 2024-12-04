package com.example.sistema_hospitalario.dao.listeners;

import com.example.sistema_hospitalario.dto.PatientDTO;

public interface OnPatientReceivedListener {
    void onPatientReceived(PatientDTO patient);
    void onError(Exception e);
}
