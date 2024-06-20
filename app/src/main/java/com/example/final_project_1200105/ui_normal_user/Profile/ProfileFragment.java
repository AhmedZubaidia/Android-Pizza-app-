package com.example.final_project_1200105.ui_normal_user.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.start_activites.login_reg.Hash;
import com.example.final_project_1200105.start_activites.login_reg.User;
import com.example.final_project_1200105.start_activites.login_reg.UserDatabaseHelper;
import com.example.final_project_1200105.ui_normal_user.Menu.SharedViewModel;

public class ProfileFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, passwordEditText;
    private Button changeButton;
    private UserDatabaseHelper dbHelper;
    private SharedViewModel sharedViewModel;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new UserDatabaseHelper(getContext());

        firstNameEditText = view.findViewById(R.id.profile_firstNameEditText);
        lastNameEditText = view.findViewById(R.id.profile_lastNameEditText);
        phoneEditText = view.findViewById(R.id.profile_phoneEditText);
        passwordEditText = view.findViewById(R.id.profile_passwordEditText);
        changeButton = view.findViewById(R.id.profile_ChangeButton);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observe the userEmail LiveData
        sharedViewModel.getUserEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String email) {
                userEmail = email;
                loadUserInfo();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        return view;
    }

    private void loadUserInfo() {
        if (userEmail != null && !userEmail.isEmpty()) {
            User user = dbHelper.getUserByEmail(userEmail);
            if (user != null) {
                firstNameEditText.setText(user.getFirstName());
                lastNameEditText.setText(user.getLastName());
                phoneEditText.setText(user.getPhoneNumber());
               // passwordEditText.setText(user.getPassword());
            } else {
                Toast.makeText(getContext(), "Failed to load user information.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUser() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateInput(firstName, lastName, phone, password)) {
            return;
        }

        String encryptedPassword = Hash.hashPassword(password);

        boolean isUpdated = dbHelper.updateUser(userEmail, firstName, lastName, encryptedPassword, phone);

        if (isUpdated) {
            Toast.makeText(getContext(), "Information updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to update information.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String firstName, String lastName, String phone, String password) {
        try {
            User tempUser = new User();
            tempUser.setFirstName(firstName);
            tempUser.setLastName(lastName);
            tempUser.setPhoneNumber(phone);
            tempUser.setPassword(password);

        } catch (IllegalArgumentException e) {

            if (e.getMessage().contains("phone number")) {
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
