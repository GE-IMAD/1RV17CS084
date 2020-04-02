package com.rvcollege.onlinevoting.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rvcollege.onlinevoting.model.User;

import java.util.ArrayList;
import java.util.List;


public class ConstDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 18;

    // Database Name
    private static final String DATABASE_NAME = "UserManager4.db";
    //private static String constname;

    // User table name
    private static final String TABLE_USER = "ConstituencyDb";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_PARTY_NAME = "party_name";
    private static final String COLUMN_CONSTITUENCY_NAME = "constituency_name";
    private static final String COLUMN_PARTY_VOTES = "party_votes";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_PARTY_NAME + " TEXT,"
            + COLUMN_CONSTITUENCY_NAME + " TEXT," + COLUMN_PARTY_VOTES + " INTEGER" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public ConstDatabase(Context context) {
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

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTY_NAME, user.getPartyName());
        values.put(COLUMN_CONSTITUENCY_NAME, user.getConstituency());
        values.put(COLUMN_PARTY_VOTES, user.getVotes());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser(String constituency) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_PARTY_NAME,
                COLUMN_CONSTITUENCY_NAME,
                COLUMN_PARTY_VOTES
        };
        // sorting orders
        String sortOrder =
                COLUMN_PARTY_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        String selection = COLUMN_CONSTITUENCY_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {constituency};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setPartyName(cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_NAME)));
                user.setConstituency(cursor.getString(cursor.getColumnIndex(COLUMN_CONSTITUENCY_NAME)));
                user.setVotes(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_VOTES))));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_PARTY_NAME,
                COLUMN_CONSTITUENCY_NAME,
                COLUMN_PARTY_VOTES
        };
        // sorting orders
        String sortOrder =
                COLUMN_PARTY_NAME + " ASC";
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
                user.setPartyName(cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_NAME)));
                user.setConstituency(cursor.getString(cursor.getColumnIndex(COLUMN_CONSTITUENCY_NAME)));
                user.setVotes(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_VOTES))));
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
     * //@param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARTY_NAME, user.getPartyName());
        values.put(COLUMN_CONSTITUENCY_NAME, user.getConstituency());
        values.put(COLUMN_PARTY_VOTES, user.getVotes());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getPartyName())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * //@param email
     * @return true/false
     */
    public boolean checkUser(String partyname) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_PARTY_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {partyname};

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
    public boolean checkUser(String partyname, String constituency) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_PARTY_NAME + " = ?" + " AND " + COLUMN_CONSTITUENCY_NAME + " = ?";

        // selection arguments
        String[] selectionArgs = {partyname,constituency};

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

    public void addVote(String partyname,String constituency) {
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL="UPDATE ConstituencyDb SET party_votes=party_votes+1 WHERE party_name = "+"'"+partyname+"'" +" AND "+"constituency_name = "+ "'"+constituency+"'";
        db.execSQL(strSQL);
        db.close();
        db.close();

    }

    /**
     * This method to check user exist or not
     *
     * //@param email
     * //@param password
     * @return true/false
     */

}