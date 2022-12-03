package com.example.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    IClickListener iClickListener;
    private ArrayList<CartList> cartLists;


    public OrderAdapter(IClickListener iClickListener, ArrayList<OrderListView> orderListViews) {
     this.iClickListener = iClickListener;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_singleview, parent, false);
        return new ViewHolder(view);

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.viewButton.setOnClickListener(view -> iClickListener.onItemClick());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return 10;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        Button viewButton;

        ViewHolder(View itemView) {
            super(itemView);
            viewButton = itemView.findViewById(R.id.view_btn);

        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

