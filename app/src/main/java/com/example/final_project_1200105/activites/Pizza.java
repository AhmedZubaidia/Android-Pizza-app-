package com.example.final_project_1200105.activites;

public class Pizza {
    private String name;
    private String description;  // Placeholder for additional details if needed
    private double price;  // Placeholder for price
    private String size;  // Placeholder for size
    private String category;  // Placeholder for category

    public Pizza(String name, String description, double price, String size, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.category = category;
    }

    // Getters and setters
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
