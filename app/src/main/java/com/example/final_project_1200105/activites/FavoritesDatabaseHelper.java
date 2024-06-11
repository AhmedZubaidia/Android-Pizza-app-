package com.example.final_project_1200105.activites;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class FavoritesDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavoriteDatabase.db";
    private static final String TABLE_FAVORITES = "FAVORITES";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_SIZE = "SIZE";
    private static final String COLUMN_CATEGORY = "CATEGORY";

    public FavoritesDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_SIZE + " TEXT,"
                + COLUMN_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public boolean insertFavorite(String userEmail, Pizza pizza) {
        if (isFavorite(userEmail, pizza.getName())) {
            return false; // Already in favorites
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_NAME, pizza.getName());
        values.put(COLUMN_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PRICE, pizza.getPrice());
        values.put(COLUMN_SIZE, pizza.getSize());
        values.put(COLUMN_CATEGORY, pizza.getCategory());

        long result = db.insert(TABLE_FAVORITES, null, values);
        if (result != -1) {
            Log.d("FavoritesDB", "Inserted favorite: " + pizza.getName());
        } else {
            Log.d("FavoritesDB", "Failed to insert favorite: " + pizza.getName());
        }
        return result != -1;
    }

    public boolean isFavorite(String userEmail, String pizzaName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_USER_EMAIL + "=? AND " + COLUMN_NAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{userEmail, pizzaName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public List<Pizza> getAllFavorites(String userEmail) {
        List<Pizza> favoriteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_USER_EMAIL + "=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        Log.d("FavoritesDB", "Query: " + selectQuery + ", UserEmail: " + userEmail);

        try {
            cursor = db.rawQuery(selectQuery, new String[]{userEmail});
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                    @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex(COLUMN_SIZE));
                    @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                    Pizza pizza = new Pizza(name, description, price, size, category);
                    favoriteList.add(pizza);
                    Log.d("FavoritesDB", "Retrieved favorite: " + pizza.getName());
                } while (cursor.moveToNext());
            } else {
                Log.d("FavoritesDB", "No favorites found for user: " + userEmail);
            }
        } catch (Exception e) {
            Log.e("FavoritesDB", "Error retrieving favorites", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return favoriteList;
    }

    public boolean deleteFavorite(String userEmail, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FAVORITES, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_NAME + " = ?", new String[]{userEmail, name});
        return result > 0;
    }
}
