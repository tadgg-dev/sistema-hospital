package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dto.PatientDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.PatientManager;


public class PatientEditActivity extends AppCompatActivity {

    private PatientManager patientManager;
    private ExecutorManager executorManager;
    private PatientDTO patientUpdate;

    private String patientEmail, addressStr, medicalCodeStr;

    private EditText editTextAddress, editTextMedicalCode;
    private Button ButtonSaveEditPatientData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);

        // Getting interface elements
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextMedicalCode = findViewById(R.id.editTextMedicalCode);

        ButtonSaveEditPatientData = findViewById(R.id.saveEditPatientData);

        // Get patient email
        patientEmail = getIntent().getStringExtra("patient_email");

        // Getting interface buttons
        ButtonSaveEditPatientData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs()){
                    updatePatient();
                }else{
                    runOnUiThread(() -> {
                        Toast.makeText(PatientEditActivity.this, "Ingrese todos los datos solicitados", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private boolean checkInputs(){
        // Strings
        addressStr = editTextAddress.getText().toString();
        medicalCodeStr = editTextMedicalCode.getText().toString();

        if(addressStr.isEmpty() || medicalCodeStr.isEmpty() ){
            return false;
        }
        return true;
    }

    private void updatePatient(){

        Integer medicalCodeInt = Integer.parseInt(medicalCodeStr);
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        patientManager = new PatientManager(executorManager.getExecutorService());
        patientManager.setContext(PatientEditActivity.this);

        // UserUpdate
        patientUpdate = new PatientDTO(null,null, null,null,medicalCodeInt,null,null,null,addressStr,null);
        patientManager.updatePatient(patientEmail, patientUpdate);

        // Go back
        Intent intent = new Intent(PatientEditActivity.this, PatientDetailActivity.class);
        intent.putExtra("patient_email", patientEmail);
        startActivity(intent);
    }
}
