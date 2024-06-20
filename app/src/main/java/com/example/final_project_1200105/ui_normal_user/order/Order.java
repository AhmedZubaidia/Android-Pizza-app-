package com.example.final_project_1200105.ui_normal_user.order;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String userEmail;
    private String dateTime;
    private String orderDetails;

    public Order(int id, String userEmail, String dateTime, String orderDetails) {
        this.id = id;
        this.userEmail = userEmail;
        this.dateTime = dateTime;
        this.orderDetails = orderDetails;
    }

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }
}
