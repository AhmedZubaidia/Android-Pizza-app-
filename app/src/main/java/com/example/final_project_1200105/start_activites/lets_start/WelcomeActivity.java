package com.example.final_project_1200105.start_activites.lets_start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.start_activites.login_reg.LoginActivity;
import com.example.final_project_1200105.start_activites.login_reg.RegistrationActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnGoRegister;
    private TextView btnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnGoRegister = findViewById(R.id.RegFromWelcomeButton);
        btnGoLogin = findViewById(R.id.sign_in_text);

        // Load the animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Start the animation on the root view of the activity
        findViewById(android.R.id.content).startAnimation(fadeIn);

        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });



        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
