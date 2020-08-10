package com.example.wastebank;

/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;




public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";
    ProgressBar progressBar;
    EditText editTextEmail , editTextPassword ,editTextName,editTextPhone;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName=findViewById(R.id.username);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword= findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.phone);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        findViewById(R.id.b_register).setOnClickListener(this);
        findViewById(R.id.log_in).setOnClickListener(this);



    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString();
        final String phone = editTextPhone.getText().toString();

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

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    finish();

                    Toast.makeText(getApplicationContext(),"User Register Successfull", Toast.LENGTH_SHORT).show();

                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Name",name);
                    user.put("Email",email);
                    user.put("Password",password);
                    user.put("phone",phone);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG,"OnSuccess: User profile is created for :"+userID);
                        }
                    });

                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                    /*Intent intent = new Intent(RegisterActivity.this , HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/

                }else{

                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(getApplicationContext(), "You are already register.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.GONE);



                }

            }
        });




    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.b_register:

                registerUser();
            break;

            case R.id.log_in:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;


        }

    }
}