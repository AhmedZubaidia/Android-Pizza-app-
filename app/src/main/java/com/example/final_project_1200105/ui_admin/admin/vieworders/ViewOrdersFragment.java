package com.example.final_project_1200105.ui_admin.admin.vieworders;

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
import com.example.final_project_1200105.ui.order.Order;
import com.example.final_project_1200105.ui.order.OrderAdapter;
import com.example.final_project_1200105.ui.order.OrdersDatabaseHelper;

import java.util.List;

public class ViewOrdersFragment extends Fragment {

    private OrdersDatabaseHelper ordersDatabaseHelper;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_orders, container, false);

        ordersDatabaseHelper = new OrdersDatabaseHelper(getActivity());
        recyclerView = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadOrders();

        return rootView;
    }

    private void loadOrders() {
        List<Order> orderList = ordersDatabaseHelper.getAllOrders();
        orderAdapter = new OrderAdapter(orderList, getActivity(), null);
        recyclerView.setAdapter(orderAdapter);
    }
}
