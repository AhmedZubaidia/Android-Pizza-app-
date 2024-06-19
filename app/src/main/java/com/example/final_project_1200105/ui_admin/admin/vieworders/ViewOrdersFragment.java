package com.example.final_project_1200105.ui_admin.admin.vieworders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui.order.Order;
import com.example.final_project_1200105.ui.order.OrderAdapter;
import com.example.final_project_1200105.ui.order.OrdersDatabaseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOrdersFragment extends Fragment {

    private static OrdersDatabaseHelper ordersDatabaseHelper;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private static TextView tvPizzaOrderCounts;
    private static TextView tvTotalIncome;
    private static TextView tvIncomePerPizzaType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_orders, container, false);

        ordersDatabaseHelper = new OrdersDatabaseHelper(getActivity());
        recyclerView = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvPizzaOrderCounts = rootView.findViewById(R.id.tvPizzaOrderCounts);
        tvTotalIncome = rootView.findViewById(R.id.tvTotalIncome);
        tvIncomePerPizzaType = rootView.findViewById(R.id.tvIncomePerPizzaType);

        loadOrders();
        displayStatistics();

        return rootView;
    }

    private void loadOrders() {
        List<Order> orderList = ordersDatabaseHelper.getAllOrders();
        orderAdapter = new OrderAdapter(orderList, getActivity(), null);
        recyclerView.setAdapter(orderAdapter);
    }

    public static void displayStatistics() {
        List<Order> orderList = ordersDatabaseHelper.getAllOrders();
        Map<String, Integer> pizzaOrderCounts = new HashMap<>();
        Map<String, Double> pizzaTotalIncome = new HashMap<>();
        double totalIncome = 0.0;

        for (Order order : orderList) {
            String orderDetails = order.getOrderDetails();
            String[] details = orderDetails.split("\n");
            String category = null;
            double totalPrice = 0.0;

            // Handle the first category separately
            for (String detail : details) {
                if (detail.trim().startsWith("Pizza Category: ")) {
                    category = detail.replace("Pizza Category: ", "").trim();
                    pizzaOrderCounts.put(category, pizzaOrderCounts.getOrDefault(category, 0) + 1);
                    break; // Exit the loop after finding the first category
                }
            }

            // Handle the total price separately
            for (String detail : details) {
                if (detail.trim().startsWith("Total Price: $")) {
                    totalPrice = Double.parseDouble(detail.replace("Total Price: $", "").trim());
                    if (category != null) { // Ensure category is not null
                        pizzaTotalIncome.put(category, pizzaTotalIncome.getOrDefault(category, 0.0) + totalPrice);
                    }
                    totalIncome += totalPrice;
                    break; // Exit the loop after finding the total price
                }
            }

            // Handle any additional categories and prices
            for (String detail : details) {
                if (detail.trim().startsWith("Pizza Category: ") && !detail.contains(category)) {
                    category = detail.replace("Pizza Category: ", "").trim();
                    pizzaOrderCounts.put(category, pizzaOrderCounts.getOrDefault(category, 0) + 1);
                } else if (detail.trim().startsWith("Total Price: $") && !detail.contains(String.valueOf(totalPrice))) {
                    totalPrice = Double.parseDouble(detail.replace("Total Price: $", "").trim());
                    if (category != null) { // Ensure category is not null
                        pizzaTotalIncome.put(category, pizzaTotalIncome.getOrDefault(category, 0.0) + totalPrice);
                    }
                    totalIncome += totalPrice;
                }
            }
        }

        StringBuilder orderCountsBuilder = new StringBuilder("Pizza Order Counts:\n");
        for (Map.Entry<String, Integer> entry : pizzaOrderCounts.entrySet()) {
            orderCountsBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        StringBuilder incomePerPizzaTypeBuilder = new StringBuilder("Income per Pizza Type:\n");
        for (Map.Entry<String, Double> entry : pizzaTotalIncome.entrySet()) {
            incomePerPizzaTypeBuilder.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }

        tvPizzaOrderCounts.setText(orderCountsBuilder.toString().trim());
        tvTotalIncome.setText("Total Income: $" + String.format("%.2f", totalIncome));
        tvIncomePerPizzaType.setText(incomePerPizzaTypeBuilder.toString().trim());
    }


}
