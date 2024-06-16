package com.example.final_project_1200105.activites.lets_start;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, Void, List<String>> {
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
    protected List<String> doInBackground(String... params) {
        try {
            String data = HttpManager.getData(params[0]);
            if (data != null) {
                return PizzaJsonParser.getObjectFromJson(data);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching data", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String> pizzaTypes) {
        ((MainActivity) activity).setProgress(false);
        ((MainActivity) activity).setButtonText("Get Started");

        if (pizzaTypes != null) {
            ((MainActivity) activity).goToLoginRegistration(pizzaTypes);
        } else {
            ((MainActivity) activity).showError();
        }
    }
}
