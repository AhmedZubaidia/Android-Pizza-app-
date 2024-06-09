package com.example.final_project_1200105.activites;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;

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
            showPizzaDetails(pizza);
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

    private void showPizzaDetails(Pizza pizza) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pizza_details_dialog, null);
        ((TextView) view.findViewById(R.id.pizza_name)).setText(pizza.getName());
        ((TextView) view.findViewById(R.id.pizza_description)).setText(pizza.getDescription());
        ((TextView) view.findViewById(R.id.pizza_price)).setText(String.valueOf(pizza.getPrice()));

        builder.setView(view)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void addToFavorites(Pizza pizza) {
        // Implement add to favorites logic
    }

    private void showOrderMenu(Pizza pizza) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.order_menu_dialog, null);
        ((TextView) view.findViewById(R.id.pizza_name)).setText(pizza.getName());
        ((TextView) view.findViewById(R.id.pizza_price)).setText(String.valueOf(pizza.getPrice()));

        // Setup size and quantity input fields and handle order submission

        builder.setView(view)
                .setPositiveButton("Submit", (dialog, id) -> {
                    // Implement order submission logic
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }
}
