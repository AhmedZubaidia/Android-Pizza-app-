package com.example.final_project_1200105.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_1200105.MainActivity2;
import com.example.final_project_1200105.R;

public class LoginActivity extends AppCompatActivity {

    private TextView btnGoSignUp;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckbox;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_EMAIL = "email";
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnGoSignUp = findViewById(R.id.signUpTextView);
        emailEditText = findViewById(R.id.LogEditText2);
        passwordEditText = findViewById(R.id.LogEditText3);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        dbHelper = new UserDatabaseHelper(this);

        // Load saved email if it exists
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, null);
        if (savedEmail != null) {
            emailEditText.setText(savedEmail);
            rememberMeCheckbox.setChecked(true);
        }

        btnGoSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.LogButton);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean rememberMe = rememberMeCheckbox.isChecked();

            // Encrypt the entered password for comparison
           String encryptedPassword = Hash.hashPassword(password);


            // Check login credentials
            if (dbHelper.checkUser(email, encryptedPassword)) {
                // Save email in shared preferences if "Remember me" is checked
                if (rememberMe) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_EMAIL, email);
                    editor.apply();
                } else {
                    // Clear saved email
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_EMAIL);
                    editor.apply();
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
