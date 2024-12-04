package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LobbyActivity extends AppCompatActivity {

    private TextView title;
    private Button buttonViewPatientList, buttonViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Getting interface elements
        buttonViewPatientList = findViewById(R.id.showListOfPatients);
        buttonViewProfile = findViewById(R.id.showMyProfile);
        title = findViewById(R.id.welcome_user);

        // Get the user's email from the Intent
        String userEmail = getIntent().getStringExtra("user_email");
        String userName = getIntent().getStringExtra("user_name");

        // Set userName
        if(userName != null){
            title.setText("¡Hola, " + userName + "!");
        }

        //  Show de next view
        buttonViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LobbyActivity.this, ProfileActivity.class);
                intent.putExtra("user_email", userEmail);
                startActivity(intent);
            }
        });

        buttonViewPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LobbyActivity.this, PatientActivity.class);
                intent.putExtra("user_email", userEmail);
                startActivity(intent);
            }
        });

    }
}
