package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dto.UserDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.UserManager;

public class ProfileActivity extends AppCompatActivity {

    private UserManager userManager;
    private ExecutorManager executorManager;

    private String userEmail;

    private TextView firstName, lastName, dni, userName, email, medicalLicence;
    private Button buttonSave, buttonChangePassword ,buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Getting interface elements
        firstName = findViewById(R.id.textViewProfileFirstName);
        lastName = findViewById(R.id.textViewProfileLastName);
        dni = findViewById(R.id.textViewProfileIdentification);
        userName = findViewById(R.id.textViewProfileUserName);
        email = findViewById(R.id.textViewProfileEmail);
        medicalLicence = findViewById(R.id.textViewProfileMedicalLicense);
        buttonSave = findViewById(R.id.editProfileData);
        buttonDelete = findViewById(R.id.deleteProfile);
        buttonChangePassword = findViewById(R.id.changePassword);

        // Get the user's email from the Intent
        userEmail = getIntent().getStringExtra("user_email");

        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        userManager = new UserManager(executorManager.getExecutorService());
        userManager.setContext(this);

        // Getting user
        getUser(userEmail);

        //  Getting interface buttons
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to edit view
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                intent.putExtra("user_email", userEmail);
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to change password view
                Intent intent = new Intent(ProfileActivity.this, ChangePassword.class);
                intent.putExtra("user_email", userEmail);
                startActivity(intent);
            }
        });
    }

    private void getUser(String userEmail) {
        userManager.getUser(userEmail, new OnUserReceivedListener() {
            @Override
            public void onUserReceived(UserDTO user) {
                // Method to set user
                if (user != null && user.getEmail().equals(userEmail)) setUser(user);
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setUser(UserDTO user) {
        if (user != null) {
            runOnUiThread(() -> {
                firstName.setText(user.getFirstName() != null ? user.getFirstName() : "");
                lastName.setText(user.getLastName() != null ? user.getLastName() : "");
                dni.setText(user.getDni() != null ? user.getDni() : "");
                userName.setText(user.getUserName() != null ? user.getUserName() : "");
                email.setText(user.getEmail() != null ? user.getEmail() : "");
                medicalLicence.setText(user.getMedical_license() != null ? user.getMedical_license().toString() : "");
            });
        }
    }

    private void deleteUser(){
        // Go to home
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        userManager.deleteUser(userEmail);
    }
}
