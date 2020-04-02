package com.rvcollege.onlinevoting.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;

import com.rvcollege.loginregister.R;
import com.rvcollege.onlinevoting.helpers.InputValidation;
import com.rvcollege.onlinevoting.sql.DatabaseHelper;

public class SecretKey extends AppCompatActivity {

    private final AppCompatActivity activity = SecretKey.this;
public String email;
public DatabaseHelper databaseHelper;
public String phone;
public String Secretkey;
public InputValidation inputValidation;
    private TextInputLayout textInputLayoutKey;
    private NestedScrollView nestedScrollView;
    private TextInputEditText textInputEditTextKey;
 AppCompatButton Enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_key);
        databaseHelper=new DatabaseHelper(this);
        inputValidation = new InputValidation(this);
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
        //constituency=pref.getString("constituency","");
        email=pref.getString("email","");
        getPhone(email);
        getSecret(email);
        Enter=(AppCompatButton) findViewById(R.id.appCompatButtonEnter);
        textInputLayoutKey =(TextInputLayout)findViewById(R.id.textInputLayoutKey);

        textInputEditTextKey = (TextInputEditText) findViewById(R.id.textInputEditTextKey);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VerifyKey()) {
                    Intent accountsIntent = new Intent(activity, Voting.class);
                    //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                    startActivity(accountsIntent);
                    finish();
                }
                else
                {
                    Snackbar.make(nestedScrollView, getString(R.string.error_secret_key), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
    public void getPhone(String email){
        phone= databaseHelper.getPhone(email);
    }
    public void getSecret(String email){
        Secretkey=databaseHelper.getKey(email);
    }
    private boolean VerifyKey() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextKey, textInputLayoutKey, "Incorrect Secret Key")) {
            return false;}
            if (databaseHelper.checkKey(email
                    , textInputEditTextKey.getText().toString().trim())) {

                //SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(this);
                //SharedPreferences.Editor editor=setting.edit();
                // editor.putString("constituency",databaseHelper.getConstituency(email));
                //editor.putString("email",textInputEditTextEmail.getText().toString().trim());
                // editor.commit();
                if (databaseHelper.getFlag(email) == 0) {

                    //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                    return true;

                }
            }
            return false;
        }
    }
