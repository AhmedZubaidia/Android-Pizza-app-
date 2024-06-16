package com.example.final_project_1200105.ui.Menu;

import java.io.Serializable;

public class Pizza implements Serializable {
    private String name;
    private String description;
    private double price;
    private String size;
    private String category;

    public Pizza(String name, String description, double price, String size, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.category = category;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
