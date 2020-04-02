package com.rvcollege.onlinevoting.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rvcollege.onlinevoting.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;//import com.google.firebase.*;
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 18;
    private DatabaseReference databaseVoters;
    // Database Name
    private static final String DATABASE_NAME = "UserManager4.db";

    // User table name
    private static final String TABLE_USER = "user";
    //private static final String


    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_ADHAAR = "user_adhaar";
    private static final String COLUMN_USER_VOTERID = "user_voterid";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_CONSTITUENCY = "user_constituency";
    private static final String COLUMN_USER_FLAG ="user_flag";
    private static final String COLUMN_SECRET_KEY="secret_key";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_ADHAAR + " TEXT," + COLUMN_USER_VOTERID + " TEXT,"+ COLUMN_USER_PHONE +" TEXT,"+ COLUMN_USER_CONSTITUENCY + " TEXT," + COLUMN_USER_FLAG + " INTEGER DEFAULT 0,"+ COLUMN_SECRET_KEY + " INTEGER"+")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        databaseVoters=FirebaseDatabase.getInstance().getReference("Voters");
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ADHAAR, user.getAdhaar());
        values.put(COLUMN_USER_VOTERID, user.getVoterId());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_CONSTITUENCY, user.getConstituency());

        Random random=new Random();
        String a=String.format("%04d",random.nextInt(10000));
        values.put(COLUMN_SECRET_KEY,a);
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
        String id= databaseVoters.push().getKey();
        databaseVoters.child(id).setValue(user);
        databaseVoters.child(id).child("secretKey").setValue(a);
        databaseVoters.child(id).child("id").removeValue();
        databaseVoters.child(id).child("votes").removeValue();
    }


    public List<User> getAllUser() {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_ADHAAR,
                COLUMN_USER_VOTERID,
                COLUMN_USER_PHONE,
                COLUMN_USER_CONSTITUENCY,
                COLUMN_SECRET_KEY
        };
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setAdhaar(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADHAAR)));
                user.setVoterId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_VOTERID)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                user.setConstituency(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CONSTITUENCY)));
                user.setSecretKey(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SECRET_KEY))));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete user record by id
            db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
            db.close();
        }
        catch (SQLException e){
            Log.e("error",e.getMessage());
        }
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkVoter(String voterid) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_VOTERID + " = ?";

        // selection argument
        String[] selectionArgs = {voterid};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkPhone(String phone) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?";

        // selection argument
        String[] selectionArgs = {phone};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkAdhaar(String adhaar) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_ADHAAR + " = ?";

        // selection argument
        String[] selectionArgs = {adhaar};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public String getConstituency(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Constituency = "";
        Cursor cursor = db.rawQuery("SELECT user_constituency FROM user WHERE user_email=?", new String[]{email + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Constituency = cursor.getString(cursor.getColumnIndex("user_constituency"));}
        cursor.close();
        return Constituency;


    }

    public void setFlag(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL="UPDATE user SET user_flag=1 WHERE user_email= "+"'"+email+"'";
        db.execSQL(strSQL);
        db.close();
    }
    public int getFlag(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        //String Constituency = "";
        int flag=0;
        Cursor cursor = db.rawQuery("SELECT user_flag FROM user WHERE user_email=?", new String[]{email + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            flag = cursor.getInt(cursor.getColumnIndex("user_flag"));}
        cursor.close();
        return flag;
    }

    public String getPhone(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Phone = "";
        Cursor cursor = db.rawQuery("SELECT user_phone FROM user WHERE user_email=?", new String[]{email + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Phone = cursor.getString(cursor.getColumnIndex("user_phone"));}
        cursor.close();
        return Phone;
    }

    public String getKey(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String key = "";
        Cursor cursor = db.rawQuery("SELECT secret_key FROM user WHERE user_email=?", new String[]{email + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            key = cursor.getString(cursor.getColumnIndex("secret_key"));}
        cursor.close();
        return key;
    }
    public boolean checkKey(String email, String key) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_SECRET_KEY + " = ?";

        // selection arguments
        String[] selectionArgs = {email, key};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkAdhaarlen(String trim) {
        if(trim.length()!=12)
            return true;
        return false;
    }

    public boolean checkPhonelen(String trim) {
        if(trim.length()!=10)
            return true;
        return false;
    }
}