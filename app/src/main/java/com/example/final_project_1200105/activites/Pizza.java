package com.example.final_project_1200105.activites;

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getCategory() {
        return category;
    }
}
