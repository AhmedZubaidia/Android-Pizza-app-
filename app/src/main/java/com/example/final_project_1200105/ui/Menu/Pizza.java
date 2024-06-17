package com.example.final_project_1200105.ui.Menu;

import java.io.Serializable;

public class Pizza implements Serializable {
    private String name;
    private String description;
    private double price;
    private String size;
    private String category;
    private boolean isSpecialOffer;
    private String offerPeriod;
    private double offerPrice;



    public Pizza(String name, String description, double price, String size, String category, boolean isSpecialOffer, String offerPeriod, double offerPrice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.category = category;
        this.isSpecialOffer = isSpecialOffer;
        this.offerPeriod = offerPeriod;
        this.offerPrice = offerPrice;
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

    public boolean isSpecialOffer() {
        return isSpecialOffer;
    }

    public void setSpecialOffer(boolean specialOffer) {
        isSpecialOffer = specialOffer;
    }

    public String getOfferPeriod() {
        return offerPeriod;
    }

    public void setOfferPeriod(String offerPeriod) {
        this.offerPeriod = offerPeriod;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }
}
