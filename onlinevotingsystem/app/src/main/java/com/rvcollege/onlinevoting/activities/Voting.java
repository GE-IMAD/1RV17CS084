package com.rvcollege.onlinevoting.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rvcollege.loginregister.R;
import com.rvcollege.onlinevoting.adapters.VotingRecylcerAdapter;
import com.rvcollege.onlinevoting.model.User;
import com.rvcollege.onlinevoting.sql.ConstDatabase;
import com.rvcollege.onlinevoting.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;



public class Voting extends AppCompatActivity {

    private AppCompatActivity activity = Voting.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private  static VotingRecylcerAdapter usersRecyclerAdapter;
    private ConstDatabase databaseHelper;
    private DatabaseHelper dhelper;
    private AppCompatButton delete;
    String constituency,email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        getSupportActionBar().setTitle("");
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
         constituency=pref.getString("constituency","");
        email=pref.getString("email","");
        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        databaseHelper = new ConstDatabase(activity);
        dhelper=new DatabaseHelper(activity);
        usersRecyclerAdapter = new VotingRecylcerAdapter(this,listUsers,databaseHelper,dhelper);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);


        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        SharedPreferences setting=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=setting.edit();
        //editor.putString("constituency",databaseHelper.getConstituency(textInputEditTextEmail.getText().toString().trim()));
        editor.putString("email1",emailFromIntent);
        editor.commit();
        textViewName.setText(emailFromIntent);
        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser(constituency));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();

            }
        }.execute();
    }
    public static void notifyAdapter(){
        usersRecyclerAdapter.notifyDataSetChanged();
    }
}
