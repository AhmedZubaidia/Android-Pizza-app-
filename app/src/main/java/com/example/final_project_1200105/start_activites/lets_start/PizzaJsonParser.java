package com.example.final_project_1200105.start_activites.lets_start;

import com.example.final_project_1200105.ui_normal_user.Menu.Pizza;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzaJsonParser {

    public static List<Pizza> getPizzasFromJson(String json) {
        List<Pizza> pizzaList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                double price = jsonObject.getDouble("price");
                String size = jsonObject.getString("size");
                String category = jsonObject.getString("category");
                boolean specialOffer = jsonObject.getBoolean("specialOffer");
                String offerPeriod = jsonObject.isNull("offerPeriod") ? null : jsonObject.getString("offerPeriod");
                double offerPrice = jsonObject.getDouble("offerPrice");

                Pizza pizza = new Pizza(name, description, price, size, category, specialOffer, offerPeriod, offerPrice);
                pizzaList.add(pizza);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return pizzaList;
    }
}
