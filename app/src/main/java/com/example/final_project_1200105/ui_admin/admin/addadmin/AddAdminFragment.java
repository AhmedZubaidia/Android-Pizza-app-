package com.example.final_project_1200105.ui_admin.admin.addadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.login_reg.Hash;
import com.example.final_project_1200105.activites.login_reg.User;
import com.example.final_project_1200105.activites.login_reg.UserDatabaseHelper;

public class AddAdminFragment extends Fragment {

    private EditText emailEditText, phoneEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button addAdminButton;
    private UserDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_admin, container, false);

        dbHelper = new UserDatabaseHelper(getActivity());

        emailEditText = rootView.findViewById(R.id.RegEditText2_admin);
        phoneEditText = rootView.findViewById(R.id.phoneEditText_admin);
        firstNameEditText = rootView.findViewById(R.id.firstNameEditText_admin);
        lastNameEditText = rootView.findViewById(R.id.lastNameEditText_admin);
        genderSpinner = rootView.findViewById(R.id.genderSpinner);
        passwordEditText = rootView.findViewById(R.id.passwordEditText_admin);
        confirmPasswordEditText = rootView.findViewById(R.id.confirmPasswordEditText_admin);
        addAdminButton = rootView.findViewById(R.id.AddButton_admin);

        // Set up the spinner with a custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        addAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAdmin();
            }
        });

        return rootView;
    }

    private void registerAdmin() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!validateInput(email, phone, firstName, lastName, password, confirmPassword, gender)) {
            return;
        }

        // Encrypt the password
        String encryptedPassword = Hash.hashPassword(password);

        // Create a new user and insert into the database
        User user = new User(email, phone, firstName, lastName, gender, encryptedPassword, true); // true for admin
        boolean isInserted = dbHelper.insertUser(user);

        if (isInserted) {
            Toast.makeText(getActivity(), "Admin added successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
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
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (e.getMessage().contains("email")) {
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

    private void clearFields() {
        emailEditText.setText("");
        phoneEditText.setText("");
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
        genderSpinner.setSelection(0);
    }
}
