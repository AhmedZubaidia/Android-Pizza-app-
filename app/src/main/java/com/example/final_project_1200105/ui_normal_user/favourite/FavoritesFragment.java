package com.example.final_project_1200105.ui_normal_user.favourite;

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
import com.example.final_project_1200105.ui_normal_user.Menu.SharedViewModel;
import com.example.final_project_1200105.ui_normal_user.Menu.Pizza;
import com.example.final_project_1200105.ui_normal_user.Menu.PizzaAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private FavoritesDatabaseHelper databaseHelper;
    private SharedViewModel sharedViewModel;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        // Initialize the SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getUserEmail().observe(getViewLifecycleOwner(), email -> {
            userEmail = email;

            databaseHelper = new FavoritesDatabaseHelper(view.getContext());

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            List<Pizza> favoritePizzas = databaseHelper.getAllFavorites(userEmail);

            pizzaAdapter = new PizzaAdapter(favoritePizzas, getContext(), userEmail);
            pizzaAdapter.setFavoritesContext(true);
            recyclerView.setAdapter(pizzaAdapter);
        });

        return view;
    }
}
