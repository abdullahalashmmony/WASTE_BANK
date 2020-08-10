package com.example.wastebank;

/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class editActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    EditText editTextUserName , editTextPhone ;
    ImageView imageView;
    Uri UriProfileImage;
    FirebaseAuth mAuth;
    String profileImageUrl;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        editTextUserName = findViewById(R.id.username);
        editTextPhone = findViewById(R.id.phone);
        imageView = findViewById(R.id.imageView);
        mAuth=FirebaseAuth.getInstance();

        loadUserInformation();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();

            }
        });




        findViewById(R.id.b_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUserInformation();

            }


        });

    }

    private void loadUserInformation() {

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            if (user.getPhotoUrl() != null) {

                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);


            }

            if (user.getDisplayName() != null) {
                editTextUserName.setText(user.getDisplayName());

            }
            if (user.getPhoneNumber() != null) {

                editTextPhone.setText(user.getPhoneNumber());
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }
    }

    private void saveUserInformation() {
            String desplayName = editTextUserName.getText().toString();
            String desplayPhone = editTextPhone.getText().toString();

            if(desplayName.isEmpty()){

                editTextUserName.setError("Name is required.");
                editTextUserName.requestFocus();
                return;
            }
        if(desplayPhone.isEmpty()){

            editTextPhone.setError("Phone Number is required.");
            editTextPhone.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
            if (user!=null && profileImageUrl !=null){

                UserProfileChangeRequest profile  = new UserProfileChangeRequest.Builder()
                        .setDisplayName(desplayName)
                        .setDisplayName(desplayPhone)
                        .setPhotoUri(Uri.parse(profileImageUrl))
                        .build();

                user.updateProfile(profile)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(editActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CHOOSE_IMAGE && resultCode ==RESULT_OK && data!= null && data.getData() !=null){

            UriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),UriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics"+System.currentTimeMillis()+".jeg");

        if (UriProfileImage!=null){

            profileImageRef.putFile(UriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            profileImageUrl = taskSnapshot.getMetadata().toString();



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(editActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();



                        }
                    });
        }
    }

    private  void showImageChooser(){

        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image."),CHOOSE_IMAGE);
    }
}
