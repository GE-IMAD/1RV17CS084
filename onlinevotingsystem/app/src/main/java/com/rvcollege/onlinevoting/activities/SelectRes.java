package com.rvcollege.onlinevoting.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;

import com.rvcollege.loginregister.R;

public class SelectRes extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton appCompatButtonBan;
    private AppCompatButton appCompatButtonMys;
    private AppCompatButton appCompatButtonBel;
    private AppCompatButton appCompatButtonGul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_res);
        initViews();
        initListeners();
    }

    private void initViews() {

        appCompatButtonBan = (AppCompatButton) findViewById(R.id.appCompatButtonBan);
        appCompatButtonMys = (AppCompatButton) findViewById(R.id.appCompatButtonMys);
        appCompatButtonBel = (AppCompatButton) findViewById(R.id.appCompatButtonBel);
        appCompatButtonGul = (AppCompatButton) findViewById(R.id.appCompatButtonGul);

    }

    private void initListeners() {
        appCompatButtonBan.setOnClickListener(this);
        appCompatButtonBel.setOnClickListener(this);
        appCompatButtonMys.setOnClickListener(this);
        appCompatButtonGul.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonBan:
                SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=setting.edit();
                editor.putString("constituency","Bangalore");
                editor.commit();
                Intent intentRegister = new Intent(getApplicationContext(), Results.class);
                startActivity(intentRegister);
                break;
            case R.id.appCompatButtonBel:
                // Navigate to RegisterActivity
                SharedPreferences setting1=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor1=setting1.edit();
                editor1.putString("constituency","Belagavi");
                editor1.commit();
                Intent intentRegister1 = new Intent(getApplicationContext(), Results.class);
                startActivity(intentRegister1);
                break;
            case R.id.appCompatButtonMys:
                // Navigate to RegisterActivity
                SharedPreferences setting2=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor2=setting2.edit();
                editor2.putString("constituency","Mysore");
                editor2.commit();
                Intent intentRegister2 = new Intent(getApplicationContext(), Results.class);
                startActivity(intentRegister2);
                break;
            case R.id.appCompatButtonGul:
                // Navigate to RegisterActivity
                SharedPreferences setting3=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor3=setting3.edit();
                editor3.putString("constituency","Gulbarga");
                editor3.commit();
                Intent intentRegister3 = new Intent(getApplicationContext(), Results.class);
                startActivity(intentRegister3);
                break;
        }
    }
}