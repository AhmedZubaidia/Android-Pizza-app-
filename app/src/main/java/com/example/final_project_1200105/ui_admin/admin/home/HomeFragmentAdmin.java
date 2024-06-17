package com.example.final_project_1200105.ui_admin.admin.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project_1200105.R;


import androidx.annotation.NonNull;

public class HomeFragmentAdmin extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        return root;
    }


}