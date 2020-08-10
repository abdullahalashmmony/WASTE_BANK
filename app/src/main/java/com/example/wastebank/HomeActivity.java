package com.example.wastebank;
/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wastebank.R.id;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
   // private ActionBarDrawerToggle mToggle;
    //BottomNavigationView bottomNavigation;
    //Button mButtonprofile;




   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        mButtonprofile = (Button) findViewById(id.nav_profile);

        mButtonprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(HomeActivity.this, profileActivity.class);

                startActivity(profileIntent);
            }

        });

    }*/





    RelativeLayout click_progress;
    RelativeLayout click_web;
    RelativeLayout click_point;
    ImageView click_about;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        BottomNavigationView bottomNavigationView = findViewById(id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case id.nav_profile:
                        Intent profileintent = new Intent(HomeActivity.this, profileActivity.class);
                        startActivity(profileintent);
                        Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();

                        break;
                    case id.nav_setting:
                        Intent settingintent = new Intent(HomeActivity.this, settingActivity.class);
                        startActivity(settingintent);
                        Toast.makeText(HomeActivity.this, "Setting", Toast.LENGTH_SHORT).show();

                        break;
                    case id.nav_logout:
                        FirebaseAuth.getInstance().signOut();//logout
                        Intent exitintent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(exitintent);
                        finish();
                        Toast.makeText(HomeActivity.this, "LogOut", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });




       click_progress = findViewById(id.b_progress);
       click_point = findViewById(id.view_point);
       click_about = findViewById(id.b_about);
       click_web = findViewById(id.urlWeb);

       click_progress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent progressIntent = new Intent(HomeActivity.this, progressActivity.class);
               startActivity(progressIntent);

           }
       });

       click_point.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent pointIntent = new Intent(HomeActivity.this, PointActivity.class);
               startActivity(pointIntent);

           }
       });
       click_web.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wastebank.weebly.com/"));
               startActivity(Getintent);

           }
       });

       click_about.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent aboutIntent = new Intent(HomeActivity.this, aboutActivity.class);
               startActivity(aboutIntent);

           }
       });


   }





}







   /* BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case id.nav_profile:
                            Intent profileIntent = new Intent(HomeActivity.this, profileActivity.class);
                            startActivity(profileIntent);
                            return true;
                        case id.nav_logout:
                            Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(logoutIntent);

                            return true;
                    }
                    return false;
                }
            };
*/

