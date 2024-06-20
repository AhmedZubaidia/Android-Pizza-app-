package com.example.final_project_1200105.ui.Menu;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PizzaDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Pizza_new_Database.db";
    private static final String TABLE_PIZZA = "PIZZAS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_SIZE = "SIZE";
    private static final String COLUMN_CATEGORY = "CATEGORY";
    private static final String COLUMN_IS_SPECIAL_OFFER = "IS_SPECIAL_OFFER";
    private static final String COLUMN_OFFER_PERIOD = "OFFER_PERIOD";
    private static final String COLUMN_OFFER_PRICE = "OFFER_PRICE";

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
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_IS_SPECIAL_OFFER + " INTEGER,"
                + COLUMN_OFFER_PERIOD + " TEXT,"
                + COLUMN_OFFER_PRICE + " REAL" + ")";
        db.execSQL(CREATE_PIZZA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_PIZZA + " ADD COLUMN " + COLUMN_IS_SPECIAL_OFFER + " INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE " + TABLE_PIZZA + " ADD COLUMN " + COLUMN_OFFER_PERIOD + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_PIZZA + " ADD COLUMN " + COLUMN_OFFER_PRICE + " REAL");
        }
    }

    public boolean insertPizza(Pizza pizza) {
        if (getPizzaByNameCategorySize(pizza.getName(), pizza.getCategory(), pizza.getSize()) != null) {
            // Pizza already exists in the database
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pizza.getName());
        values.put(COLUMN_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PRICE, pizza.getPrice());
        values.put(COLUMN_SIZE, pizza.getSize());
        values.put(COLUMN_CATEGORY, pizza.getCategory());
        values.put(COLUMN_IS_SPECIAL_OFFER, pizza.isSpecialOffer() ? 1 : 0);
        values.put(COLUMN_OFFER_PERIOD, pizza.getOfferPeriod());
        values.put(COLUMN_OFFER_PRICE, pizza.getOfferPrice());
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
                        cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SPECIAL_OFFER)) == 1,
                        cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_PERIOD)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_OFFER_PRICE))
                );
                pizzaList.add(pizza);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pizzaList;
    }

    public Pizza getPizzaByNameCategorySize(String name, String category, String size) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PIZZA + " WHERE " + COLUMN_NAME + " = ? AND " + COLUMN_CATEGORY + " = ? AND " + COLUMN_SIZE + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name, category, size});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") Pizza pizza = new Pizza(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SIZE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SPECIAL_OFFER)) == 1,
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_PERIOD)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_OFFER_PRICE))
            );
            cursor.close();
            return pizza;
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean updatePizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pizza.getName());
        values.put(COLUMN_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_PRICE, pizza.getPrice());
        values.put(COLUMN_SIZE, pizza.getSize());
        values.put(COLUMN_CATEGORY, pizza.getCategory());
        values.put(COLUMN_IS_SPECIAL_OFFER, pizza.isSpecialOffer() ? 1 : 0);
        values.put(COLUMN_OFFER_PERIOD, pizza.getOfferPeriod());
        values.put(COLUMN_OFFER_PRICE, pizza.getOfferPrice());
        int result = db.update(TABLE_PIZZA, values, COLUMN_NAME + " = ? AND " + COLUMN_CATEGORY + " = ? AND " + COLUMN_SIZE + " = ?", new String[]{pizza.getName(), pizza.getCategory(), pizza.getSize()});
        return result > 0;
    }

    public boolean deletePizza(String name, String category, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PIZZA, COLUMN_NAME + " = ? AND " + COLUMN_CATEGORY + " = ? AND " + COLUMN_SIZE + " = ?", new String[]{name, category, size});
        return result > 0;
    }

    public void deletePizzasNotInList(List<Pizza> pizzas) {
        Set<String> pizzaIdentifiers = new HashSet<>();
        for (Pizza pizza : pizzas) {
            pizzaIdentifiers.add(pizza.getName() + "-" + pizza.getCategory() + "-" + pizza.getSize());
        }

        List<Pizza> allPizzas = getAllPizzas();
        for (Pizza pizza : allPizzas) {
            String identifier = pizza.getName() + "-" + pizza.getCategory() + "-" + pizza.getSize();
            if (!pizza.isSpecialOffer() && !pizzaIdentifiers.contains(identifier)) {
                deletePizza(pizza.getName(), pizza.getCategory(), pizza.getSize());
            }
        }
    }
}
