package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dto.UserDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.UserManager;

public class RegisterActivity extends AppCompatActivity {

    private UserDTO newUserDTO;
    private UserManager userManager;
    private ExecutorManager executorManager;

    private String nameStr, lastNameStr, identificationStr, userNameStr, emailStr, passwordStr, medicalLicenseStr;
    private Integer medicalLicenseInteger;
    private Boolean userCreated = false, isUserExist = true;

    private EditText editTextName, editTextLastName, editTextIdentification, editTextUserName, editTextEmail, editTextPassword, editTextMedicalLicense;
    private Button buttonSaveRegister;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializo de inputs
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextIdentification = findViewById(R.id.editTextIdentification);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextMedicalLicense = findViewById(R.id.editTextMedicalLicense);

        // Button Register
        buttonSaveRegister = findViewById(R.id.buttonSaveRegister);

        // Button Listener
        buttonSaveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Strings
                nameStr = editTextName.getText().toString();
                lastNameStr = editTextLastName.getText().toString();
                identificationStr = editTextIdentification.getText().toString();
                userNameStr = editTextUserName.getText().toString();
                emailStr = editTextEmail.getText().toString();
                passwordStr = editTextPassword.getText().toString();
                medicalLicenseStr = editTextMedicalLicense.getText().toString();

                // Valido si el mail ingresado existe
                validateUserExist(emailStr);
            }
        });
    }

    private boolean createUser(String name, String lastName, String dni, String userName, String email, String password, String medical_license) {

        // Validacion de los inputs
        if (name.isEmpty() || lastName.isEmpty() || dni.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty() || medical_license.isEmpty()) {
            runOnUiThread(() -> {
                Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
            });
            return false;
        }

        // Parseo
        medicalLicenseInteger = Integer.parseInt(medical_license);

        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        userManager = new UserManager(executorManager.getExecutorService());
        userManager.setContext(this);

        // Creacion usuario
        newUserDTO = new UserDTO(name, lastName, dni, email, userName, password, medicalLicenseInteger);
        userManager.createUser(newUserDTO);

        return true;
    }

    private void validateUserExist(String email) {
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        userManager = new UserManager(executorManager.getExecutorService());
        userManager.setContext(this);

        // Getting user
        userManager.getUser(email, new OnUserReceivedListener() {
            @Override
            public void onUserReceived(UserDTO user) {
                if (user == null) {
                    userCreated = createUser(nameStr, lastNameStr, identificationStr, userNameStr, emailStr, passwordStr, medicalLicenseStr);
                    if (userCreated) {
                        Intent intent = new Intent(RegisterActivity.this, LobbyActivity.class);
                        intent.putExtra("user_email", emailStr);
                        intent.putExtra("user_name", userNameStr);
                        startActivity(intent);
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "El email que ingreso ya existe", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
