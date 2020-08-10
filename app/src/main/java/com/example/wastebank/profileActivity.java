package com.example.wastebank;

/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class profileActivity extends AppCompatActivity {

    TextView name , email , phone;
    StorageReference storageReference;
Button edit;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        name = findViewById(R.id.username);
        edit = findViewById(R.id.b_edit);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();



        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    phone.setText(documentSnapshot.getString("phone"));
                    name.setText(documentSnapshot.getString("Name"));
                    email.setText(documentSnapshot.getString("Email"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(profileActivity.this, editActivity.class);
                startActivity(edit);

            }
        });


    }
}
