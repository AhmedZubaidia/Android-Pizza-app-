package com.example.final_project_1200105.ui.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_1200105.R;

public class PizzaDetailsFragment extends Fragment {

    private Pizza pizza;

    public static PizzaDetailsFragment newInstance(Pizza pizza) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("pizza", pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);

        if (getArguments() != null) {
            pizza = (Pizza) getArguments().getSerializable("pizza");
        }

        ((TextView) view.findViewById(R.id.pizza_name)).setText(pizza.getName());
        ((TextView) view.findViewById(R.id.pizza_description)).setText(pizza.getDescription());
        ((TextView) view.findViewById(R.id.pizza_price)).setText(String.format("$%.2f", pizza.getPrice()));
        ((TextView) view.findViewById(R.id.pizza_size)).setText(pizza.getSize());
        ((TextView) view.findViewById(R.id.pizza_category)).setText(pizza.getCategory());

        return view;
    }
}
