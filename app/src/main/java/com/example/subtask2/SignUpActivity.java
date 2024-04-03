package com.example.subtask2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextUsername, editTextPassword, editTextConfirmPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup_activity); // Fragment signup Xml file

        // Initialize your views here
        editTextFullName = findViewById(R.id.fullName);
        editTextUsername = findViewById(R.id.signUpUsername);
        editTextPassword = findViewById(R.id.signUpPassword);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        Button signUpButton = findViewById(R.id.createAccountButton);

        databaseHelper = new DatabaseHelper(this); // this context for curent fragemnt

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String fullName = editTextFullName.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validation logic
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Please check your inputs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Additional logic to handle user registration
        if (!databaseHelper.checkUser(username, password)) {
            databaseHelper.addUser(fullName, username, password);
            Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
        }
    }
}
