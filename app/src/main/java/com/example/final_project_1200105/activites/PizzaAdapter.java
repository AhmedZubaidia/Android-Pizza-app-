package com.example.final_project_1200105.activites;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui.gallery.OrderMenuFragment;
import com.example.final_project_1200105.ui.gallery.PizzaDetailsFragment;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<Pizza> pizzaList;
    private Context context;
    private FragmentManager fragmentManager;
    private int fragmentContainerId;

    public PizzaAdapter(List<Pizza> pizzaList, Context context, FragmentManager fragmentManager, int fragmentContainerId) {
        this.pizzaList = pizzaList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.fragmentContainerId = fragmentContainerId;
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
        PizzaDetailsFragment pizzaDetailsFragment = PizzaDetailsFragment.newInstance(pizza);
        fragmentManager.beginTransaction()
                .replace(fragmentContainerId, pizzaDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void addToFavorites(Pizza pizza) {
        // Implement add to favorites logic
    }

    private void showOrderMenu(Pizza pizza) {
        OrderMenuFragment orderMenuFragment = OrderMenuFragment.newInstance(pizza);
        fragmentManager.beginTransaction()
                .replace(fragmentContainerId, orderMenuFragment)
                .addToBackStack(null)
                .commit();
    }
}
