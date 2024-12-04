package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dao.listeners.OnPatientsReceivedListener;
import com.example.sistema_hospitalario.dto.PatientDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.PatientManager;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    private PatientDTO newPatientDTO;
    private PatientManager patientManager;
    private ExecutorManager executorManager;

    private List<String> patientNamesList;
    private List<PatientDTO> patientsList;
    private ArrayAdapter<String> adapter;
    private String email;

    private Button buttonAddPatient;
    private ListView listView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        listView = findViewById(R.id.listViewPatients);
        buttonAddPatient = findViewById(R.id.buttonAddPatient);
        patientNamesList = new ArrayList<>();
        patientsList = new ArrayList<>();

        // Get the user's email from the Intent
        email = getIntent().getStringExtra("user_email");

        showPatients();

        buttonAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, PatientAddActivity.class);
                intent.putExtra("user_email", email);
                startActivity(intent);
            }
        });
    }

    private void showPatients(){
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        patientManager = new PatientManager(executorManager.getExecutorService());
        patientManager.setContext(this);

        patientManager.getAllPatientsByDoctorEmail(email, new OnPatientsReceivedListener() {
            @Override
            public void onPatientsReceived(List<PatientDTO> patients) {
                patientsList = patients;
                for (PatientDTO patient : patients) {
                    patientNamesList.add(patient.getFirstName() + " " + patient.getLastName());
                }

                adapter = new ArrayAdapter<>(PatientActivity.this, android.R.layout.simple_list_item_1, patientNamesList);
                listView.setAdapter(adapter);

                runOnUiThread(() -> {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            PatientDTO selectedPatient = patients.get(position);
                            Intent intent = new Intent(PatientActivity.this, PatientDetailActivity.class);
                            intent.putExtra("patient_email", selectedPatient.getEmail());
                            intent.putExtra("user_email", email);
                            startActivity(intent);
                        }
                    });
                });
            }

            @Override
            public void onError(Exception e) { e.printStackTrace(); }
        });
    }
}
