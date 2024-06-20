package com.example.final_project_1200105.ui_admin.admin.addoffers;

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
import com.example.final_project_1200105.ui_normal_user.Menu.Pizza;
import com.example.final_project_1200105.ui_normal_user.Menu.PizzaDatabaseHelper;

import java.util.List;

public class AddOffersFragment extends Fragment {

    private Spinner pizzaTypeSpinner, categorySpinner, sizeSpinner;
    private EditText offerPeriodEditText, totalPriceEditText;
    private Button addOfferButton;
    private PizzaDatabaseHelper pizzaDatabaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offers, container, false);

        pizzaDatabaseHelper = new PizzaDatabaseHelper(getContext());

        pizzaTypeSpinner = view.findViewById(R.id.pizzaTypeSpinner);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        sizeSpinner = view.findViewById(R.id.sizeSpinner);
        offerPeriodEditText = view.findViewById(R.id.offerPeriodEditText);
        totalPriceEditText = view.findViewById(R.id.totalPriceEditText);
        addOfferButton = view.findViewById(R.id.addOfferButton);

        // Load data into spinners
        loadPizzaTypes();
        setupCategorySpinner();
        setupSizeSpinner();

        addOfferButton.setOnClickListener(v -> addOffer());

        return view;
    }

    private void loadPizzaTypes() {
        // Fetch pizza types from the database
        List<Pizza> pizzaList = pizzaDatabaseHelper.getAllPizzas();
        String[] pizzaNames = new String[pizzaList.size()];

        for (int i = 0; i < pizzaList.size(); i++) {
            pizzaNames[i] = pizzaList.get(i).getName();
        }

        // Set pizza types to the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, pizzaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(adapter);
    }

    private void setupCategorySpinner() {
        // Set categories to the spinner from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array_offer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void setupSizeSpinner() {
        // Set sizes to the spinner from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.size_array_add_offer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);
    }

    private void addOffer() {
        // Get data from the views
        String pizzaType = pizzaTypeSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String size = sizeSpinner.getSelectedItem().toString();
        String offerPeriod = offerPeriodEditText.getText().toString().trim();
        String totalPrice = totalPriceEditText.getText().toString().trim();

        if (pizzaType.isEmpty() || category.isEmpty() || size.isEmpty() || offerPeriod.isEmpty() || totalPrice.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(totalPrice);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid price", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if a pizza with the same name, category, and size exists
        Pizza existingPizza = pizzaDatabaseHelper.getPizzaByNameCategorySize(pizzaType, category, size);
        if (existingPizza != null) {
            // Update existing pizza
            existingPizza.setSpecialOffer(true);
            existingPizza.setOfferPeriod(offerPeriod);
            existingPizza.setOfferPrice(price);
            boolean isUpdated = pizzaDatabaseHelper.updatePizza(existingPizza);
            if (isUpdated) {
                Toast.makeText(getActivity(), "Special offer updated successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(getActivity(), "Failed to update special offer", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Insert new pizza
            Pizza pizza = new Pizza(pizzaType, "Special offer", price, size, category, true, offerPeriod, price);
            boolean isInserted = pizzaDatabaseHelper.insertPizza(pizza);
            if (isInserted) {
                Toast.makeText(getActivity(), "Special offer added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(getActivity(), "Failed to add special offer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearFields() {
        pizzaTypeSpinner.setSelection(0);
        categorySpinner.setSelection(0);
        sizeSpinner.setSelection(0);
        offerPeriodEditText.setText("");
        totalPriceEditText.setText("");
    }
}