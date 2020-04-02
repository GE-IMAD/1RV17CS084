package com.rvcollege.onlinevoting.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.view.View;

import com.rvcollege.loginregister.R;

public class wait extends AppCompatActivity {
    AppCompatButton Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        Logout=(AppCompatButton) findViewById(R.id.appCompatButtonLogout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountsIntent = new Intent(getApplicationContext(), LoginActivity.class);
                //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                startActivity(accountsIntent);
                finish();

            }
        });


    }
    @Override
    public void onBackPressed(){
        return;
    }

}
