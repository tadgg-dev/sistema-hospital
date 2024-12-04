package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dao.listeners.OnPatientReceivedListener;
import com.example.sistema_hospitalario.dto.PatientDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.PatientManager;


public class PatientDetailActivity extends AppCompatActivity {

    private PatientManager patientManager;
    private ExecutorManager executorManager;

    private String patientEmail,userEmail;

    private Button buttonGoToUpdatePatient, buttonDeletePatient;
    private TextView patientDetailFistName, patientDetailLastName, patientDetailDNI, patientDetailMedicalCode, patientDetailDateOfBirthday, patientDetailGender, patientDetailPhone, patientDetailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);


        patientDetailFistName = findViewById(R.id.patientDetailFistName);
        patientDetailLastName = findViewById(R.id.patientDetailLastName);
        patientDetailDNI = findViewById(R.id.patientDetailDNI);
        patientDetailMedicalCode = findViewById(R.id.patientDetailMedicalCode);
        patientDetailDateOfBirthday = findViewById(R.id.patientDetailDateOfBirthday);
        patientDetailGender = findViewById(R.id.patientDetailGender);
        patientDetailPhone = findViewById(R.id.patientDetailPhone);
        patientDetailAddress = findViewById(R.id.patientDetailAddress);

        buttonDeletePatient = findViewById(R.id.buttonDeletePatient);
        buttonGoToUpdatePatient = findViewById(R.id.buttonGoToUpdatePatient);

        patientEmail= getIntent().getStringExtra("patient_email");
        userEmail= getIntent().getStringExtra("user_email");

        getUser();

        buttonDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePatient();
            }
        });

        buttonGoToUpdatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to change password view
                Intent intent = new Intent(PatientDetailActivity.this, PatientEditActivity.class);
                intent.putExtra("patient_email", patientEmail);
                startActivity(intent);
            }
        });
    }

    private void getUser(){
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        patientManager = new PatientManager(executorManager.getExecutorService());
        patientManager.setContext(this);

        patientManager.getPatient(patientEmail,  new OnPatientReceivedListener() {
            @Override
            public void onPatientReceived(PatientDTO patient) {
                if(patient != null) {
                    runOnUiThread(() -> {
                        patientDetailFistName.setText(patient.getFirstName());
                        patientDetailLastName.setText(patient.getLastName());
                        patientDetailDNI.setText(patient.getDni());
                        patientDetailMedicalCode.setText(patient.getUser_medical_code().toString());
                        patientDetailDateOfBirthday.setText(patient.getDateOfBirth());
                        patientDetailGender.setText(patient.getGender());
                        patientDetailPhone.setText(patient.getPhone());
                        patientDetailAddress.setText(patient.getAddress());
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void deletePatient(){
        // Go to Patient
        Intent intent = new Intent(PatientDetailActivity.this, PatientActivity.class);
        intent.putExtra("user_email", userEmail);
        startActivity(intent);
        patientManager.deletePatient(patientEmail);
    }
}

