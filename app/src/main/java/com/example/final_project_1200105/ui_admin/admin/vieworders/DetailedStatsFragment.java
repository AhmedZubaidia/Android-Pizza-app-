package com.example.final_project_1200105.ui_admin.admin.vieworders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui.order.Order;
import com.example.final_project_1200105.ui.order.OrdersDatabaseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailedStatsFragment extends Fragment {

    private OrdersDatabaseHelper ordersDatabaseHelper;
    private TextView tvDetailedStats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detailed_stats, container, false);

        ordersDatabaseHelper = new OrdersDatabaseHelper(getActivity());
        tvDetailedStats = rootView.findViewById(R.id.tvDetailedStats);

        displayDetailedStatistics();

        return rootView;
    }

    private void displayDetailedStatistics() {
        List<Order> orderList = ordersDatabaseHelper.getAllOrders();
        Map<String, Integer> pizzaOrderCounts = new HashMap<>();
        Map<String, Double> pizzaTotalIncome = new HashMap<>();
        double totalIncome = 0.0;

        for (Order order : orderList) {
            String orderDetails = order.getOrderDetails();
            String[] details = orderDetails.split("\n");
            String category = "";
            double totalPrice = 0.0;

            for (String detail : details) {
                if (detail.trim().startsWith("Pizza Category: ")) {
                    category = detail.replace("Pizza Category: ", "").trim();
                    pizzaOrderCounts.put(category, pizzaOrderCounts.getOrDefault(category, 0) + 1);
                } else if (detail.trim().startsWith("Total Price: $")) {
                    totalPrice = Double.parseDouble(detail.replace("Total Price: $", "").trim());
                    pizzaTotalIncome.put(category, pizzaTotalIncome.getOrDefault(category, 0.0) + totalPrice);
                    totalIncome += totalPrice;
                }
            }
        }

        StringBuilder detailedStatsBuilder = new StringBuilder();
        detailedStatsBuilder.append("Pizza Order Counts:\n");
        for (Map.Entry<String, Integer> entry : pizzaOrderCounts.entrySet()) {
            detailedStatsBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        detailedStatsBuilder.append("\nIncome per Pizza Type:\n");
        for (Map.Entry<String, Double> entry : pizzaTotalIncome.entrySet()) {
            detailedStatsBuilder.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }
        detailedStatsBuilder.append("\nTotal Income: $").append(String.format("%.2f", totalIncome));

        tvDetailedStats.setText(detailedStatsBuilder.toString());
    }
}
