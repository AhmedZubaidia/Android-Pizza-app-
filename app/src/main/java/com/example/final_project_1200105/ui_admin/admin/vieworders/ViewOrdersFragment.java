package com.example.final_project_1200105.ui_admin.admin.vieworders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.ui.order.Order;
import com.example.final_project_1200105.ui.order.OrderAdapter;
import com.example.final_project_1200105.ui.order.OrdersDatabaseHelper;

import java.util.List;

public class ViewOrdersFragment extends Fragment {

    private static OrdersDatabaseHelper ordersDatabaseHelper;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private static TextView tvTotalOrders;
    private static TextView tvTotalIncome;
    private Button btnViewDetailedStats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_orders, container, false);

        ordersDatabaseHelper = new OrdersDatabaseHelper(getActivity());
        recyclerView = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvTotalOrders = rootView.findViewById(R.id.tvTotalOrders);
        tvTotalIncome = rootView.findViewById(R.id.tvTotalIncome);
        btnViewDetailedStats = rootView.findViewById(R.id.btnViewDetailedStats);

        btnViewDetailedStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_View_Orders_to_staticsFragment);
            }
        });

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
        int totalOrders = orderList.size();
        double totalIncome = 0.0;

        for (Order order : orderList) {
            String orderDetails = order.getOrderDetails();
            String[] details = orderDetails.split("\n");
            for (String detail : details) {
                if (detail.trim().startsWith("Total Price: $")) {
                    double totalPrice = Double.parseDouble(detail.replace("Total Price: $", "").trim());
                    totalIncome += totalPrice;
                }
            }
        }

        tvTotalOrders.setText("Total Orders: " + totalOrders);
        tvTotalIncome.setText("Total Income: $" + String.format("%.2f", totalIncome));
    }
}
