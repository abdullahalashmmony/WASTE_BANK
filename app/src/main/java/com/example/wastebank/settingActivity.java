package com.example.wastebank;
/*
created by: Abdullah Omar Alashmony
jul/2020
Graduation-AASTMT (2020)
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class settingActivity extends AppCompatActivity {

    Button changeLang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();



        setContentView(R.layout.activity_setting);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(getResources().getString(R.string.app_name));
        changeLang = findViewById(R.id.changeLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });



    }

    private void showChangeLanguageDialog (){
    final String[] listItems = {"English", "العربية"};
        AlertDialog.Builder mBuldir = new AlertDialog.Builder(settingActivity.this);
        mBuldir.setTitle("Choose Language");
        mBuldir.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){

                    setLocale("En");
                            recreate();
                }
                else if (i==1){

                    setLocale("Ar");
                            recreate();
                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuldir.create();
        mDialog.show();

    }

    private void setLocale (String lang){

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang", "");
        setLocale(language);

    }

}
