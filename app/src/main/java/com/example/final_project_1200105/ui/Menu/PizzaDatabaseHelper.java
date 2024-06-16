package com.example.final_project_1200105.ui.Menu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PizzaDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PizzaDatabase.db";
    private static final String TABLE_PIZZA = "PIZZAS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_SIZE = "SIZE";
    private static final String COLUMN_CATEGORY = "CATEGORY";

    public PizzaDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PIZZA_TABLE = "CREATE TABLE " + TABLE_PIZZA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_SIZE + " TEXT,"
                + COLUMN_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_PIZZA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIZZA);
        onCreate(db);
    }

    public boolean insertPizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pizza.getName());
        values.put(COLUMN_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PRICE, pizza.getPrice());
        values.put(COLUMN_SIZE, pizza.getSize());
        values.put(COLUMN_CATEGORY, pizza.getCategory());
        long result = db.insert(TABLE_PIZZA, null, values);
        return result != -1;
    }

    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PIZZA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Pizza pizza = new Pizza(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SIZE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
                );
                pizzaList.add(pizza);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pizzaList;
    }

    public boolean updatePizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pizza.getName());
        values.put(COLUMN_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PRICE, pizza.getPrice());
        values.put(COLUMN_SIZE, pizza.getSize());
        values.put(COLUMN_CATEGORY, pizza.getCategory());
        int result = db.update(TABLE_PIZZA, values, COLUMN_NAME + " = ?", new String[]{pizza.getName()});
        return result > 0;
    }

    public boolean deletePizza(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PIZZA, COLUMN_NAME + " = ?", new String[]{name});
        return result > 0;
    }
}
