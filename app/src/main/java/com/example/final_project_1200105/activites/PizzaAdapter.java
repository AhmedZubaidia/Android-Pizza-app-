package com.example.final_project_1200105.activites;

import android.content.Context;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui.gallery.PizzaDetailsFragment;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<Pizza> pizzaList;
    private Context context;

    public PizzaAdapter(List<Pizza> pizzaList, Context context) {
        this.pizzaList = pizzaList;
        this.context = context;
    }

    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.nameTextView.setText(pizza.getName());
        holder.detailsButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("pizza", pizza);
            Navigation.findNavController(v).navigate(R.id.action_nav_Menu_to_pizzaDetailsFragment, bundle);
        });
        holder.addToFavoritesButton.setOnClickListener(v -> {
            addToFavorites(pizza);
        });
        holder.orderButton.setOnClickListener(v -> {
            showOrderMenu(pizza);
        });
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public void updateList(List<Pizza> newList) {
        pizzaList = newList;
        notifyDataSetChanged();
    }

    public class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button detailsButton;
        Button addToFavoritesButton;
        Button orderButton;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            addToFavoritesButton = itemView.findViewById(R.id.addToFavoritesButton);
            orderButton = itemView.findViewById(R.id.orderButton);
        }
    }

    private void addToFavorites(Pizza pizza) {
        // Implement add to favorites logic
    }

    private void showOrderMenu(Pizza pizza) {
        // Implement order menu dialog
    }
}
