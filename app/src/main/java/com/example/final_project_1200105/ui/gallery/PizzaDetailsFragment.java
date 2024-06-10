package com.example.final_project_1200105.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.Pizza;

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
        ((TextView) view.findViewById(R.id.pizza_price)).setText(String.valueOf(pizza.getPrice()));

        RadioGroup sizeRadioGroup = view.findViewById(R.id.size_radio_group);
        CheckBox veggieCheckBox = view.findViewById(R.id.veggie_checkbox);
        CheckBox chickenCheckBox = view.findViewById(R.id.chicken_checkbox);
        CheckBox beefCheckBox = view.findViewById(R.id.beef_checkbox);
        CheckBox othersCheckBox = view.findViewById(R.id.others_checkbox);
        Button orderButton = view.findViewById(R.id.order_button);

        // Set initial states based on pizza properties
        // Set initial sizes and categories (assuming some default values)
        // This can be more detailed depending on the pizza properties

        orderButton.setOnClickListener(v -> {
            // Handle order logic
            // Collect selected options
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            boolean isVeggie = veggieCheckBox.isChecked();
            boolean isChicken = chickenCheckBox.isChecked();
            boolean isBeef = beefCheckBox.isChecked();
            boolean isOthers = othersCheckBox.isChecked();

            // Example order handling
            // Show a summary or proceed to the order confirmation screen
        });

        return view;
    }
}
