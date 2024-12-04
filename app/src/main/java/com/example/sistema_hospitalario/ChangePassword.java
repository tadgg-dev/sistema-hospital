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

public class ChangePassword extends AppCompatActivity {

    private UserManager userManager;
    private ExecutorManager executorManager;
    private UserDTO userUpdate;

    private EditText editTextFirstPassword, editTextSecondPassword;
    private String firstPassword, secondPassword, userEmailCheck;

    private Button saveChangePasswordButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextFirstPassword = findViewById(R.id.editTextNewFirstPassword);
        editTextSecondPassword = findViewById(R.id.editTextNewSecondPassword);
        saveChangePasswordButton = findViewById(R.id.buttonSaveChangePassword);

        // Get user id
        userEmailCheck = getIntent().getStringExtra("user_email");

        saveChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputs()){
                    updatePassword();
                }else{
                    runOnUiThread(() -> {
                        Toast.makeText(ChangePassword.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private Boolean checkInputs(){
        firstPassword = editTextFirstPassword.getText().toString();
        secondPassword = editTextSecondPassword.getText().toString();

        if(!firstPassword.isEmpty() && !secondPassword.isEmpty()){
            if(firstPassword.equals(secondPassword)){
                return true;
            }
        }
        return false;
    }

    private void updatePassword(){
        // Executor
        executorManager = new ExecutorManager();

        // UserManager
        userManager = new UserManager(executorManager.getExecutorService());
        userManager.setContext(ChangePassword.this);

        // UserUpdate
        userUpdate = new UserDTO(null,null, null,null,null,firstPassword,0);
        userManager.updateUser(userEmailCheck, userUpdate);

        // Go back
        Intent intent = new Intent(ChangePassword.this, ProfileActivity.class);
        intent.putExtra("user_email", userEmailCheck);
        startActivity(intent);
    }
}

