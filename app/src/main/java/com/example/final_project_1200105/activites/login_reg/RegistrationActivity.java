package com.example.final_project_1200105.activites.login_reg;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.final_project_1200105.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button signUpButton;
    private TextView btnGoSignIn;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbHelper = new UserDatabaseHelper(this);

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

        if (!validateInput(email, phone, firstName, lastName, password, confirmPassword , gender)) {
            return;
        }

        // Encrypt the password
        String encryptedPassword = Hash.hashPassword(password);
        Log.d("REG_DEBUG", "Email: " + email + ", Hashed Password: " + encryptedPassword);

        // Create a new user and insert into the database
        User user = new User(email, phone, firstName, lastName, gender, encryptedPassword);
        boolean isInserted = dbHelper.insertUser(user);

        if (isInserted) {
            Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            emailEditText.setError("Email already exists");
        }
    }

    private boolean validateInput(String email, String phone, String firstName, String lastName, String password, String confirmPassword, String gender) {
        try {
            User tempUser = new User();
            tempUser.setEmail(email);
            tempUser.setPhoneNumber(phone);
            tempUser.setFirstName(firstName);
            tempUser.setLastName(lastName);
            tempUser.setPassword(password);
            tempUser.setGender(gender);

            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
                return false;
            }

        } catch (IllegalArgumentException e) {

            if (e.getMessage().contains("Invalid gender")) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else if (e.getMessage().contains("email")) {

                emailEditText.setError(e.getMessage());

            } else if (e.getMessage().contains("phone number")) {

                phoneEditText.setError(e.getMessage());

            } else if (e.getMessage().contains("First name")) {

                firstNameEditText.setError(e.getMessage());

            } else if (e.getMessage().contains("Last name")) {

                lastNameEditText.setError(e.getMessage());

            } else if (e.getMessage().contains("Password")) {

                passwordEditText.setError(e.getMessage());
            }
            return false;
        }

        return true;
    }
}
