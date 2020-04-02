package com.rvcollege.onlinevoting.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rvcollege.loginregister.R;
import com.rvcollege.onlinevoting.helpers.InputValidation;
import com.rvcollege.onlinevoting.model.User;
import com.rvcollege.onlinevoting.sql.Gulbarga;


public class EcActivity4 extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = EcActivity4.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutPartyName;
    //private TextInputLayout textInputLayoutEmail;
    //private TextInputLayout textInputLayoutPassword;
    //private TextInputLayout textInputLayoutConfirmPassword;
    //private TextInputLayout textInputLayoutAdhaar;
    // private TextInputLayout textInputLayoutVoterId;
    //private TextInputLayout textInputLayoutPhone;
    private Spinner spinner1;

    private TextInputEditText textInputEditTextPartyName;
    //private TextInputEditText textInputEditTextEmail;
    //private TextInputEditText textInputEditTextPassword;
    //private TextInputEditText textInputEditTextConfirmPassword;
    //private TextInputEditText textInputEditTextAdhaar;
    //private TextInputEditText textInputEditTextVoterId;
    //private TextInputEditText textInputEditTextPhone;

    private AppCompatButton appCompatButtonInsert;
    //private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    //private ConstDatabase databaseHelper1;
    //private Belagavidb databaseHelper2;
    //private Mysore databaseHelper3;
    private Gulbarga databaseHelper4;
    private User user;
    private String selected;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec4);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutPartyName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        //textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        //textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        //textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        //textInputLayoutAdhaar = (TextInputLayout) findViewById(R.id.textInputLayoutAdhaar);
        //textInputLayoutVoterId = (TextInputLayout) findViewById(R.id.textInputLayoutVoterId);
        //textInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayoutPhone);

        textInputEditTextPartyName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        //textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        //textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        //textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        //textInputEditTextAdhaar = (TextInputEditText) findViewById(R.id.textInputEditTextAdhaar);
        //textInputEditTextVoterId = (TextInputEditText) findViewById(R.id.textInputEditTextVoterId);
        //textInputEditTextPhone = (TextInputEditText) findViewById(R.id.textInputEditTextPhone);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.constituency_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        appCompatButtonInsert = (AppCompatButton) findViewById(R.id.appCompatButtonInsert);

        //appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonInsert.setOnClickListener(this);
        //appCompatTextViewLoginLink.setOnClickListener(this);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);


        databaseHelper4 = new Gulbarga(activity);

        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonInsert:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPartyName, textInputLayoutPartyName, getString(R.string.error_message_name))) {
            return;
        }
        /*if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextAdhaar, textInputLayoutAdhaar, getString(R.string.error_message_adhaar))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextVoterId, textInputLayoutVoterId, getString(R.string.error_message_voterId))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_phone))) {
            return;
        }*/

        if (!databaseHelper4.checkUser(textInputEditTextPartyName.getText().toString().trim())) {

            user.setPartyName(textInputEditTextPartyName.getText().toString().trim());
            /*user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            user.setAdhaar(textInputEditTextAdhaar.getText().toString().trim());
            user.setVoterId(textInputEditTextVoterId.getText().toString().trim());
            user.setPhone(textInputEditTextPhone.getText().toString().trim());*/
            user.setConstituency(selected);
            //databaseHelper.getConst(selected);
            databaseHelper4.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else if(databaseHelper4.checkUser(textInputEditTextPartyName.getText().toString().trim())) {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }



        /*else if(databaseHelper.checkVoter(textInputEditTextVoterId.getText().toString().trim())) {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_voterId_exists), Snackbar.LENGTH_LONG).show();
        }
        else if(databaseHelper.checkAdhaar(textInputEditTextAdhaar.getText().toString().trim())) {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, "Adhaar Already Exists", Snackbar.LENGTH_LONG).show();
        }
        else if(databaseHelper.checkPhone(textInputEditTextPhone.getText().toString().trim())) {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, "Phone Already Exists", Snackbar.LENGTH_LONG).show();}*/}
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextPartyName.setText(null);
        /*textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
        textInputEditTextAdhaar.setText(null);
        textInputEditTextVoterId.setText(null);
        textInputEditTextPhone.setText(null);*/

    }
}
