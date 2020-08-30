package com.example.gamez;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    //Activity Tag for logging
    private final static String TAG = "Register";

    EditText mEmail;
    EditText mPassword;
    EditText mPasswordCheck;

    FirebaseAuth fAuth;
    Button mRegisterBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPasswordCheck = findViewById(R.id.passwordCheck);
        mRegisterBtn = findViewById(R.id.registerBtn);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //email input error handling
                String email = mEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email must be populated");
                    return;
                }

                //password input error handling. This should be expanded.
                String password = mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password must be populated");
                    return;
                }

                //password match error handling
                String passwordCheck = mPasswordCheck.getText().toString().trim();
                if(!password.equals(passwordCheck)) {
                    mPasswordCheck.setError("Passwords do not match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create the user in firebase. Log error if user already exists.
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this,
                                    "User Created",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

            }
        });
    }
}