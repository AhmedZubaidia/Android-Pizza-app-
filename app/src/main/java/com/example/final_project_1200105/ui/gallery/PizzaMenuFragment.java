package com.example.final_project_1200105.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.Pizza;
import com.example.final_project_1200105.activites.PizzaAdapter;

import java.util.ArrayList;
import java.util.List;

public class PizzaMenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<Pizza> pizzaList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pizzaList = new ArrayList<>();
        pizzaAdapter = new PizzaAdapter(pizzaList, getContext());
        recyclerView.setAdapter(pizzaAdapter);

        // Load mock pizza types
        loadMockPizzaTypes();

        return view;
    }

    private void loadMockPizzaTypes() {
        pizzaList.add(new Pizza("Margarita", "Classic Margarita pizza with fresh tomatoes and mozzarella", 8.99, "Medium", "Veggie"));
        pizzaList.add(new Pizza("Neapolitan", "Authentic Neapolitan pizza with San Marzano tomatoes", 9.99, "Large", "Veggie"));
        pizzaList.add(new Pizza("Hawaiian", "Sweet and savory Hawaiian pizza with pineapple and ham", 10.99, "Small", "Chicken"));
        pizzaList.add(new Pizza("Pepperoni", "Spicy Pepperoni pizza with mozzarella and marinara sauce", 11.99, "Medium", "Beef"));
        pizzaList.add(new Pizza("New York Style", "Thin crust New York style pizza with a classic cheese topping", 12.99, "Large", "Veggie"));
        pizzaList.add(new Pizza("Calzone", "Folded Calzone pizza stuffed with ricotta and salami", 13.99, "Medium", "Beef"));
        pizzaList.add(new Pizza("Tandoori Chicken Pizza", "Spicy Tandoori chicken pizza with red onions and cilantro", 14.99, "Small", "Chicken"));
        pizzaList.add(new Pizza("BBQ Chicken Pizza", "Tangy BBQ chicken pizza with red onions and cilantro", 15.99, "Large", "Chicken"));
        pizzaList.add(new Pizza("Seafood Pizza", "Delicious Seafood pizza with shrimp and calamari", 16.99, "Medium", "Seafood"));
        pizzaList.add(new Pizza("Vegetarian Pizza", "Healthy Vegetarian pizza with bell peppers, olives, and onions", 17.99, "Large", "Veggie"));
        pizzaList.add(new Pizza("Buffalo Chicken Pizza", "Spicy Buffalo chicken pizza with blue cheese dressing", 18.99, "Medium", "Chicken"));
        pizzaList.add(new Pizza("Mushroom Truffle Pizza", "Gourmet Mushroom Truffle pizza with a rich truffle sauce", 19.99, "Small", "Veggie"));
        pizzaList.add(new Pizza("Pesto Chicken Pizza", "Savory Pesto chicken pizza with sun-dried tomatoes", 20.99, "Large", "Chicken"));

        pizzaAdapter.notifyDataSetChanged();
    }
}
