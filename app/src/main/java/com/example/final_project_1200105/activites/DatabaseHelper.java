package com.example.final_project_1200105.activites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT NOT NULL," +
                    UserContract.UserEntry.COLUMN_NAME_PHONE + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_LAST_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_GENDER + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
    }

    public boolean insertUser(String email, String phone, String firstName, String lastName, String gender, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_NAME_PHONE, phone);
        values.put(UserContract.UserEntry.COLUMN_NAME_FIRST_NAME, firstName);
        values.put(UserContract.UserEntry.COLUMN_NAME_LAST_NAME, lastName);
        values.put(UserContract.UserEntry.COLUMN_NAME_GENDER, gender);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);

        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection

        return newRowId != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {UserContract.UserEntry._ID};

        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ? AND " +
                UserContract.UserEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = null;
        try {
            cursor = db.query(
                    UserContract.UserEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            boolean userExists = cursor.getCount() > 0;
            Log.d("DatabaseHelper", "User exists: " + userExists);
            return userExists;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error checking user", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
