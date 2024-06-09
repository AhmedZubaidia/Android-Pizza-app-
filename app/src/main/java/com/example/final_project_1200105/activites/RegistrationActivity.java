package com.example.final_project_1200105.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_1200105.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button signUpButton;
    private TextView btnGoSignIn;
    private DatabaseHelper dbHelper;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^05\\d{8}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.RegEditText2);
        phoneEditText = findViewById(R.id.phoneEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.RegButton);
        btnGoSignIn = findViewById(R.id.SignInTextView);

        // Set up the spinner with a custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btnGoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!validateInput(email, phone, firstName, lastName, password, confirmPassword)) {
            return;
        }

        // Encrypt the password
        String encryptedPassword = hashPassword(password);

        // Insert the user into the database
        boolean isInserted = dbHelper.insertUser(email, phone, firstName, lastName, gender, encryptedPassword);

        if (isInserted) {
            Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed! Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String email, String phone, String firstName, String lastName, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email");
            return false;
        }

        if (TextUtils.isEmpty(phone) || !PHONE_PATTERN.matcher(phone).matches()) {
            phoneEditText.setError("Invalid phone number");
            return false;
        }

        if (TextUtils.isEmpty(firstName) || firstName.length() < 3) {
            firstNameEditText.setError("First name must be at least 3 characters");
            return false;
        }

        if (TextUtils.isEmpty(lastName) || lastName.length() < 3) {
            lastNameEditText.setError("Last name must be at least 3 characters");
            return false;
        }

        if (TextUtils.isEmpty(password) || !PASSWORD_PATTERN.matcher(password).matches()) {
            passwordEditText.setError("Password must be at least 8 characters and include at least 1 letter and 1 number");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
