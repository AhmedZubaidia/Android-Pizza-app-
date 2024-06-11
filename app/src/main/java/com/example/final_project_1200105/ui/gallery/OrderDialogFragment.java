package com.example.final_project_1200105.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.Pizza;

public class OrderDialogFragment extends DialogFragment {

    private Pizza pizza;

    public static OrderDialogFragment newInstance(Pizza pizza) {
        OrderDialogFragment fragment = new OrderDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("pizza", pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_dialog, container, false);

        if (getArguments() != null) {
            pizza = (Pizza) getArguments().getSerializable("pizza");
        }

        ((TextView) view.findViewById(R.id.pizza_name)).setText(pizza.getName());
        ((TextView) view.findViewById(R.id.pizza_price)).setText(String.format("$%.2f", pizza.getPrice()));
        ((TextView) view.findViewById(R.id.pizza_size)).setText(pizza.getSize());

        EditText quantityEditText = view.findViewById(R.id.quantity_edit_text);
        Button submitButton = view.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(v -> {
            String quantityStr = quantityEditText.getText().toString();
            if (quantityStr.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            double totalPrice = pizza.getPrice() * quantity;

            // Show order confirmation
            Toast.makeText(getContext(), "Order Confirmed:\n" +
                    "Pizza: " + pizza.getName() + "\n" +
                    "Size: " + pizza.getSize() + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Total Price: $" + totalPrice, Toast.LENGTH_LONG).show();

            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
