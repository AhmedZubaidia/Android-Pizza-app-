package com.example.final_project_1200105.ui.order;

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

public class OrdersDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OrdersDatabase.db";
    private static final String TABLE_ORDERS = "ORDERS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    private static final String COLUMN_DATE_TIME = "DATE_TIME";
    private static final String COLUMN_ORDER_DETAILS = "ORDER_DETAILS";

    public OrdersDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_DATE_TIME + " TEXT,"
                + COLUMN_ORDER_DETAILS + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public boolean insertOrder(String userEmail, String dateTime, String orderDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_DATE_TIME, dateTime);
        values.put(COLUMN_ORDER_DETAILS, orderDetails);

        long result = db.insert(TABLE_ORDERS, null, values);
        if (result != -1) {
            Log.d("OrdersDB", "Inserted order for: " + userEmail + " at " + dateTime);
        } else {
            Log.d("OrdersDB", "Failed to insert order for: " + userEmail);
        }
        return result != -1;
    }

    public List<Order> getAllOrders(String userEmail) {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_USER_EMAIL + "=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        Log.d("OrdersDB", "Query: " + selectQuery + ", UserEmail: " + userEmail);

        try {
            cursor = db.rawQuery(selectQuery, new String[]{userEmail});
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
                    @SuppressLint("Range") String orderDetails = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_DETAILS));
                    Order order = new Order(id, userEmail, dateTime, orderDetails);
                    orderList.add(order);
                    Log.d("OrdersDB", "Retrieved order: " + order.getId() + " at " + order.getDateTime());
                } while (cursor.moveToNext());
            } else {
                Log.d("OrdersDB", "No orders found for user: " + userEmail);
            }
        } catch (Exception e) {
            Log.e("OrdersDB", "Error retrieving orders", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return orderList;
    }

    public Order getOrderDetails(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});
        Order order = null;
        try {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL));
                @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
                @SuppressLint("Range") String orderDetails = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_DETAILS));
                order = new Order(id, userEmail, dateTime, orderDetails);
                Log.d("OrdersDB", "Retrieved order details: " + order.getId() + " at " + order.getDateTime());
            } else {
                Log.d("OrdersDB", "No order found with ID: " + orderId);
            }
        } catch (Exception e) {
            Log.e("OrdersDB", "Error retrieving order details", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return order;
    }

    public boolean deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(orderId)});
        if (result > 0) {
            Log.d("OrdersDB", "Deleted order with ID: " + orderId);
        } else {
            Log.d("OrdersDB", "Failed to delete order with ID: " + orderId);
        }
        return result > 0;
    }


    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        Log.d("OrdersDB", "Query: " + selectQuery);

        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL));
                    @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
                    @SuppressLint("Range") String orderDetails = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_DETAILS));
                    Order order = new Order(id, userEmail, dateTime, orderDetails);
                    orderList.add(order);
                    Log.d("OrdersDB", "Retrieved order: " + order.getId() + " at " + order.getDateTime());
                } while (cursor.moveToNext());
            } else {
                Log.d("OrdersDB", "No orders found");
            }
        } catch (Exception e) {
            Log.e("OrdersDB", "Error retrieving orders", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return orderList;
    }


}
