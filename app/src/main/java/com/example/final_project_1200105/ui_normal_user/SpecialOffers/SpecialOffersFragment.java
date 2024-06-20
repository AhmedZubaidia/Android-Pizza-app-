package com.example.final_project_1200105.ui_normal_user.SpecialOffers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui_normal_user.Menu.Pizza;
import com.example.final_project_1200105.ui_normal_user.Menu.PizzaAdapter;
import com.example.final_project_1200105.ui_normal_user.Menu.PizzaDatabaseHelper;
import com.example.final_project_1200105.ui_normal_user.Menu.SharedViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class SpecialOffersFragment extends Fragment {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private PizzaDatabaseHelper pizzaDatabaseHelper;

    private SharedViewModel sharedViewModel;
    private String userEmail;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_offers, container, false);

        pizzaDatabaseHelper = new PizzaDatabaseHelper(view.getContext());

        recyclerView = view.findViewById(R.id.recyclerViewSpecialOffers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch all pizzas and filter out the special offers
        List<Pizza> allPizzas = pizzaDatabaseHelper.getAllPizzas();
        List<Pizza> specialOffers = allPizzas.stream()
                .filter(Pizza::isSpecialOffer)
                .collect(Collectors.toList());

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getUserEmail().observe(getViewLifecycleOwner(), email -> {
                    userEmail = email;

            pizzaAdapter = new PizzaAdapter(specialOffers, getContext(), userEmail); // Replace with actual user email
            recyclerView.setAdapter(pizzaAdapter);


        });

        return view;
    }
}
