package com.example.final_project_1200105.ui_normal_user.order;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project_1200105.R;


public class OrderDetailsFragment extends Fragment {

    private Order order;

    public static OrderDetailsFragment newInstance(Order order) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("order", order);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_details, container, false);

        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable("order");
        }

        ((TextView) view.findViewById(R.id.details_order_id)).setText(order.getOrderDetails());

        return view;
    }
}
