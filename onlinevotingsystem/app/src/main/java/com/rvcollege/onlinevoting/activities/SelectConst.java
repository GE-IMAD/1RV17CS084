package com.rvcollege.onlinevoting.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;

import com.rvcollege.loginregister.R;

public class SelectConst extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton appCompatButtonVer;
    private AppCompatButton appCompatButtonAdd;
    private AppCompatButton appCompatButtonRes;
    private AppCompatButton appCompatButtonEle;
    public static int flagele=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_const);
        initViews();
        initListeners();
    }

    private void initViews() {

        appCompatButtonVer = (AppCompatButton) findViewById(R.id.appCompatButtonVerify);
        appCompatButtonAdd = (AppCompatButton) findViewById(R.id.appCompatButtonAddParty);
        appCompatButtonRes = (AppCompatButton) findViewById(R.id.appCompatButtonResults);
        appCompatButtonEle = (AppCompatButton) findViewById(R.id.appCompatButtonElection);
        if(flagele==0){
            //flagele=1;
            appCompatButtonEle.setText("START ELECTION");
            //break;
        }
        else if(flagele==1) {
            //flagele=0;
            appCompatButtonEle.setText("STOP ELECTION");
            //break;
        }
    }

    private void initListeners() {
        appCompatButtonVer.setOnClickListener(this);
        appCompatButtonAdd.setOnClickListener(this);
        appCompatButtonRes.setOnClickListener(this);
        appCompatButtonEle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonVerify:
                Intent intentRegister = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.appCompatButtonAddParty:
                // Navigate to RegisterActivity
                Intent intentRegister1 = new Intent(getApplicationContext(), EcActivity.class);
                startActivity(intentRegister1);
                break;
            case R.id.appCompatButtonResults:
                // Navigate to RegisterActivity
                Intent intentRegister2 = new Intent(getApplicationContext(), SelectRes.class);
                startActivity(intentRegister2);
                break;
            case R.id.appCompatButtonElection:
                if(flagele==0){
                    flagele=1;
                    appCompatButtonEle.setText("STOP ELECTION");
                    break;
                }
                else if(flagele==1){
                    flagele=0;
                    appCompatButtonEle.setText("START ELECTION");
                    break;
                }
        }
    }
}