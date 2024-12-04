package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dto.UserDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.UserManager;

public class ProfileEditActivity extends AppCompatActivity {

    private UserManager userManager;
    private ExecutorManager executorManager;
    private UserDTO userUpdate;

    private Button buttonSave;

    private EditText firstName, lastName, dni, userName, medicalLicense;
    private String firstNameStr, lastNameStr, dniStr, userNameStr, userId, userEmailCheck;
    private Integer medicalLicenceInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // Getting interface elements
        firstName = findViewById(R.id.editTextProfileFirstName);
        lastName = findViewById(R.id.editTextProfileLastName);
        dni = findViewById(R.id.editTextProfileIdentification);
        userName = findViewById(R.id.editTextProfileUserName);
        medicalLicense = findViewById(R.id.editTextProfileMedicalLicense);
        buttonSave = findViewById(R.id.saveEditProfileData);

        // Get user id
        userEmailCheck = getIntent().getStringExtra("user_email");

        // Getting interface buttons
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs()){
                    updateUser();
                }else{
                    runOnUiThread(() -> {
                        Toast.makeText(ProfileEditActivity.this, "Ingrese todos los datos solicitados", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private boolean checkInputs(){
        // Strings
        firstNameStr = firstName.getText().toString();
        lastNameStr = lastName.getText().toString();
        dniStr = dni.getText().toString();
        userNameStr = userName.getText().toString();
        medicalLicenceInt = Integer.parseInt(medicalLicense.getText().toString());

        if(firstNameStr.isEmpty() || lastNameStr.isEmpty() || dniStr.isEmpty() || userNameStr.isEmpty() || medicalLicenceInt.toString().isEmpty()){
            return false;
        }
        return true;
    }

    private void updateUser(){
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        userManager = new UserManager(executorManager.getExecutorService());
        userManager.setContext(ProfileEditActivity.this);

        // UserUpdate
        userUpdate = new UserDTO(firstNameStr,lastNameStr, dniStr,null,userNameStr,null,medicalLicenceInt);
        userManager.updateUser(userEmailCheck, userUpdate);

        // Go back
        Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
        intent.putExtra("user_email", userEmailCheck);
        startActivity(intent);
    }

}
