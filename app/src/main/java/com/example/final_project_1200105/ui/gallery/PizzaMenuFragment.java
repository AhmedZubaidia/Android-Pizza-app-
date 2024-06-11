package com.example.final_project_1200105.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.Pizza;
import com.example.final_project_1200105.activites.PizzaAdapter;
import com.example.final_project_1200105.activites.PizzaDatabaseHelper;
import com.example.final_project_1200105.activites.SharedViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class PizzaMenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<Pizza> pizzaList;
    private SearchView searchView;
    private Spinner spinnerCategory, spinnerSize;
    private PizzaDatabaseHelper databaseHelper;
    private SharedViewModel sharedViewModel;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);

        databaseHelper = new PizzaDatabaseHelper(view.getContext());

        searchView = view.findViewById(R.id.searchView);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerSize = view.findViewById(R.id.spinnerSize);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getUserEmail().observe(getViewLifecycleOwner(), email -> {
            userEmail = email;
            // Initialize and populate pizza data
            initializePizzaData();
            pizzaList = databaseHelper.getAllPizzas();
            if (pizzaList == null) {
                pizzaList = List.of(); // Initialize to an empty list if null
            }
            pizzaAdapter = new PizzaAdapter(pizzaList, getContext(), userEmail);
            recyclerView.setAdapter(pizzaAdapter);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterPizzas(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPizzas(newText);
                return false;
            }
        });

        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterPizzas(searchView.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerCategory.setOnItemSelectedListener(spinnerListener);
        spinnerSize.setOnItemSelectedListener(spinnerListener);

        return view;
    }

    private void filterPizzas(String query) {
        if (pizzaList == null || pizzaList.isEmpty()) {
            return; // Exit if pizzaList is null or empty
        }

        List<Pizza> filteredList = pizzaList.stream()
                .filter(pizza -> {
                    if (query.isEmpty()) {
                        return true;
                    } else {
                        try {
                            double enteredPrice = Double.parseDouble(query);
                            return pizza.getPrice() <= enteredPrice;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                })
                .filter(pizza -> spinnerCategory.getSelectedItem().toString().equals("All") || pizza.getCategory().equalsIgnoreCase(spinnerCategory.getSelectedItem().toString()))
                .filter(pizza -> spinnerSize.getSelectedItem().toString().equals("All") || pizza.getSize().equalsIgnoreCase(spinnerSize.getSelectedItem().toString()))
                .collect(Collectors.toList());

        pizzaAdapter.updateList(filteredList);
    }

    private void initializePizzaData() {
        // Check if the pizza table is empty before inserting data
        if (databaseHelper.getAllPizzas().isEmpty()) {
            databaseHelper.insertPizza(new Pizza("Margarita", "Classic Margarita pizza with fresh tomatoes and mozzarella", 8.99, "Medium", "Veggie"));
            databaseHelper.insertPizza(new Pizza("Neapolitan", "Authentic Neapolitan pizza with San Marzano tomatoes", 9.99, "Large", "Veggie"));
            databaseHelper.insertPizza(new Pizza("Hawaiian", "Sweet and savory Hawaiian pizza with pineapple and ham", 10.99, "Small", "Chicken"));
            databaseHelper.insertPizza(new Pizza("Pepperoni", "Spicy Pepperoni pizza with mozzarella and marinara sauce", 11.99, "Medium", "Beef"));
            databaseHelper.insertPizza(new Pizza("New York Style", "Thin crust New York style pizza with a classic cheese topping", 12.99, "Large", "Veggie"));
            databaseHelper.insertPizza(new Pizza("Calzone", "Folded Calzone pizza stuffed with ricotta and salami", 13.99, "Medium", "Beef"));
            databaseHelper.insertPizza(new Pizza("Tandoori Chicken Pizza", "Spicy Tandoori chicken pizza with red onions and cilantro", 14.99, "Small", "Chicken"));
            databaseHelper.insertPizza(new Pizza("BBQ Chicken Pizza", "Tangy BBQ chicken pizza with red onions and cilantro", 15.99, "Large", "Chicken"));
            databaseHelper.insertPizza(new Pizza("Seafood Pizza", "Delicious Seafood pizza with shrimp and calamari", 16.99, "Medium", "Seafood"));
            databaseHelper.insertPizza(new Pizza("Vegetarian Pizza", "Healthy Vegetarian pizza with bell peppers, olives, and onions", 17.99, "Large", "Veggie"));
            databaseHelper.insertPizza(new Pizza("Buffalo Chicken Pizza", "Spicy Buffalo chicken pizza with blue cheese dressing", 18.99, "Medium", "Chicken"));
            databaseHelper.insertPizza(new Pizza("Mushroom Truffle Pizza", "Gourmet Mushroom Truffle pizza with a rich truffle sauce", 19.99, "Small", "Veggie"));
            databaseHelper.insertPizza(new Pizza("Pesto Chicken Pizza", "Savory Pesto chicken pizza with sun-dried tomatoes", 20.99, "Large", "Chicken"));
        }
    }
}
