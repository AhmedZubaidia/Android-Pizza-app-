package com.example.final_project_1200105.activites;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }
}
