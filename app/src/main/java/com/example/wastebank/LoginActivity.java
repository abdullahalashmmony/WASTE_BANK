package com.example.wastebank;

/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editTextEmail , editTextPassword;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.log_in).setOnClickListener(this);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword= findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);




    }

    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required.");
            editTextEmail.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a Valid Email.");
            editTextEmail.requestFocus();
            return;


        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
            return;

        }

        if(password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;

            case R.id.log_in:
                userLogin();

        }
    }
}


