package com.example.final_project_1200105.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_1200105.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnGetStarted;
    ProgressBar progressBar;

    private static final String BASE_URL = "https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);  // Make sure this layout has ProgressBar and Button

        // Initialize Button and ProgressBar
        btnGetStarted = findViewById(R.id.lets_start_button);
        progressBar = findViewById(R.id.progressBar);

        // Set OnClickListener for the button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute(BASE_URL);
            }
        });
    }

    // Method to set the button text
    public void setButtonText(String text) {
        btnGetStarted.setText(text);
    }

    // Method to show or hide the progress bar
    public void setProgress(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    // Method to show an error message
    public void showError() {
        Toast.makeText(MainActivity.this, "Failed to load pizza types. Please try again.", Toast.LENGTH_SHORT).show();
    }

    // Method to navigate to LoginActivity with the pizza types
    public void goToLoginRegistration(List<String> pizzaTypes) {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        intent.putStringArrayListExtra("pizzaTypes", new ArrayList<>(pizzaTypes));
        startActivity(intent);
    }
}
