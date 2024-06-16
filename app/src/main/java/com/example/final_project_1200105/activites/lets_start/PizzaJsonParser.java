package com.example.final_project_1200105.activites.lets_start;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzaJsonParser {
    public static List<String> getObjectFromJson(String json) {
        List<String> pizzaTypes;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("types");
            pizzaTypes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                pizzaTypes.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return pizzaTypes;
    }
}
