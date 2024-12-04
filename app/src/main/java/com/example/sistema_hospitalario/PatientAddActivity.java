package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dao.listeners.OnPatientReceivedListener;
import com.example.sistema_hospitalario.dto.PatientDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.PatientManager;


public class PatientAddActivity extends AppCompatActivity {

    private PatientManager patientManager;
    private ExecutorManager executorManager;
    private PatientDTO patientUpdate;

    private String userEmail, firstNameStr, lastNameStr, dniStr, emailStr, medicalCodeStr, brithDayStr, genderStr, phoneStr, addresStr;
    private Boolean patientCreated;
    private Integer medicalCodeInteger;

    private Button buttonSaveNewPatient;
    private EditText patientAddFistName, patientAddLastName, patientAddDNI, patientAddEmail, patientAddMedicalCode, patientAddDateOfBirthday, patientAddGender, patientAddPhone, patientAddAddress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);

        patientAddFistName = findViewById(R.id.patientAddFistName);
        patientAddLastName = findViewById(R.id.patientAddLastName);
        patientAddDNI = findViewById(R.id.patientAddDNI);
        patientAddEmail = findViewById(R.id.patientAddEmail);
        patientAddMedicalCode = findViewById(R.id.patientAddMedicalCode);
        patientAddDateOfBirthday = findViewById(R.id.patientAddDateOfBirthday);
        patientAddGender = findViewById(R.id.patientAddGender);
        patientAddPhone = findViewById(R.id.patientAddPhone);
        patientAddAddress = findViewById(R.id.patientAddAddress);

        userEmail = getIntent().getStringExtra("user_email");

        buttonSaveNewPatient = findViewById(R.id.buttonAddPatient);

        buttonSaveNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Strings
                firstNameStr = patientAddFistName.getText().toString();
                lastNameStr = patientAddLastName.getText().toString();
                dniStr = patientAddDNI.getText().toString();
                emailStr = patientAddEmail.getText().toString();
                medicalCodeStr = patientAddMedicalCode.getText().toString();
                brithDayStr = patientAddDateOfBirthday.getText().toString();
                genderStr = patientAddGender.getText().toString();
                phoneStr = patientAddPhone.getText().toString();
                addresStr = patientAddAddress.getText().toString();

                // Valido si el mail ingresado existe
                validatePatientExist(emailStr);
            }
        });
    }

    private void validatePatientExist(String email){
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        patientManager = new PatientManager(executorManager.getExecutorService());
        patientManager.setContext(this);

        patientManager.getPatient(email,  new OnPatientReceivedListener() {
            @Override
            public void onPatientReceived(PatientDTO patient) {
                if(patient == null) {
                    patientCreated = createPatient();
                    //ACA ME QUEDE revisar inputas
                    if(patientCreated){
                        Intent intent = new Intent(PatientAddActivity.this, PatientActivity.class);
                        intent.putExtra("user_email", userEmail);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }


    private boolean createPatient() {

        // Validacion de los inputs
        if (firstNameStr.isEmpty() || lastNameStr.isEmpty() || dniStr.isEmpty() || emailStr.isEmpty() || medicalCodeStr.isEmpty() || brithDayStr.isEmpty() || genderStr.isEmpty() || phoneStr.isEmpty() || addresStr.isEmpty()) {
            runOnUiThread(() -> {
                Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
            });
            return false;
        }

        // Parseo
        medicalCodeInteger = Integer.parseInt(medicalCodeStr);

        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        patientManager = new PatientManager(executorManager.getExecutorService());
        patientManager.setContext(this);

        // Strings
        firstNameStr = patientAddFistName.getText().toString();
        lastNameStr = patientAddLastName.getText().toString();
        dniStr = patientAddDNI.getText().toString();
        emailStr = patientAddEmail.getText().toString();
        medicalCodeStr = patientAddMedicalCode.getText().toString();
        brithDayStr = patientAddDateOfBirthday.getText().toString();
        genderStr = patientAddGender.getText().toString();
        phoneStr = patientAddPhone.getText().toString();
        addresStr = patientAddAddress.getText().toString();

        // Creacion usuario
        patientUpdate = new PatientDTO(firstNameStr,lastNameStr,dniStr,emailStr,medicalCodeInteger,brithDayStr,genderStr,phoneStr,addresStr,userEmail);
        patientManager.createPatient(patientUpdate);

        return true;
    }
}
