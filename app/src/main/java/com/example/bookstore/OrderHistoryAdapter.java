package com.example.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {


    ArrayList<OrderHistoryList> orderHistoryLists;
    Context context;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistoryList> orderHistoryLists) {
        this.context = context;
        this.orderHistoryLists = orderHistoryLists;

    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_singleview, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
             OrderHistoryList orderHistoryList = orderHistoryLists.get(position);
             holder.title.setText(orderHistoryList.getTitle()+ "  x"+orderHistoryList.getCount());
             if (orderHistoryList.getPrice()>=0) {
                int totalPrice = orderHistoryList.getCount()* orderHistoryList.getPrice();
                holder.totalPrice.setText("£"+String.valueOf(totalPrice));
             }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return orderHistoryLists.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView title;
        TextView count;
        TextView totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.history_book_title);
            this.totalPrice = itemView.findViewById(R.id.history_total_price);
          }

        public TextView getCount() {
            return count;
        }

        public void setCount(TextView count) {
            this.count = count;
        }
        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(TextView orderPrice) {
            this.totalPrice = totalPrice;
        }



    }

}

