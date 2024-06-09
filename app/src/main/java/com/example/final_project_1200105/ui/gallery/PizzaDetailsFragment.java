package com.example.final_project_1200105.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.Pizza;

import java.io.Serializable;

public class PizzaDetailsFragment extends Fragment {

    private static final String ARG_PIZZA = "pizza";

    private Pizza pizza;

    public static PizzaDetailsFragment newInstance(Pizza pizza) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PIZZA, (Serializable) pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);

        if (getArguments() != null) {
            pizza = (Pizza) getArguments().getSerializable(ARG_PIZZA);
        }

        ((TextView) view.findViewById(R.id.pizza_name)).setText(pizza.getName());
        ((TextView) view.findViewById(R.id.pizza_description)).setText(pizza.getDescription());
        ((TextView) view.findViewById(R.id.pizza_price)).setText(String.valueOf(pizza.getPrice()));

        return view;
    }
}
