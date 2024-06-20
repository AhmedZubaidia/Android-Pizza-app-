package com.example.final_project_1200105.start_activites.lets_start;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.final_project_1200105.ui_normal_user.Menu.Pizza;
import com.example.final_project_1200105.ui_normal_user.Menu.PizzaDatabaseHelper;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, Void, List<Pizza>> {
    private static final String TAG = "ConnectionAsyncTask";
    private Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("Connecting...");
        ((MainActivity) activity).setProgress(true);
    }

    @Override
    protected List<Pizza> doInBackground(String... params) {
        try {
            String data = HttpManager.getData(params[0]);
            if (data != null) {
                return PizzaJsonParser.getPizzasFromJson(data);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching data", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Pizza> pizzaList) {
        ((MainActivity) activity).setProgress(false);
        ((MainActivity) activity).setButtonText("Get Started");

        if (pizzaList != null) {
            PizzaDatabaseHelper pizzaDatabaseHelper = new PizzaDatabaseHelper(activity);
            for (Pizza pizza : pizzaList) {
                pizzaDatabaseHelper.insertPizza(pizza);
            }
            pizzaDatabaseHelper.deletePizzasNotInList(pizzaList);
            Toast.makeText(activity, "Pizzas inserted successfully", Toast.LENGTH_SHORT).show();
            ((MainActivity) activity).goToWelcomeActivity();
        } else {
            ((MainActivity) activity).showError();
        }
    }
}
