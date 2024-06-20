package com.example.final_project_1200105.ui_normal_user.order;

import static com.example.final_project_1200105.ui_admin.admin.vieworders.ViewOrdersFragment.displayStatistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_1200105.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private Context context;
    private OrdersDatabaseHelper ordersDatabaseHelper;
    private String userEmail;

    public OrderAdapter(List<Order> orderList, Context context, String userEmail) {
        this.orderList = orderList;
        this.context = context;
        this.userEmail = userEmail;
        ordersDatabaseHelper = new OrdersDatabaseHelper(context);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.emailTextView.setText(order.getUserEmail());
        holder.dateTimeTextView.setText(order.getDateTime());

        holder.detailsButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", order);
            Navigation.findNavController(v).navigate(R.id.action_nav_View_Orders_to_orderDetailsFragment, bundle);
        });

        holder.deleteButton.setOnClickListener(v -> {
            deleteOrder(order, position);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateList(List<Order> newList) {
        orderList = newList;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        TextView dateTimeTextView;
        Button detailsButton;
        Button deleteButton;

        public OrderViewHolder(View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    private void deleteOrder(Order order, int position) {
        boolean isDeleted = ordersDatabaseHelper.deleteOrder(order.getId());
        if (isDeleted) {
            orderList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orderList.size());
            Toast.makeText(context, "Order deleted successfully", Toast.LENGTH_SHORT).show();
            displayStatistics();
        } else {
            Toast.makeText(context, "Failed to delete order", Toast.LENGTH_SHORT).show();
        }
    }
}
