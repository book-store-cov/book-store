package com.example.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.bookList.OnClickListener;
import com.example.bookstore.orders.OnOrderClickListener;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    OnOrderClickListener orderClickListener;
    private ArrayList<CartList> cartLists;

    ArrayList<OrderListView> orderListData;
    OnClickListener listener;
    View view;


    public OrderAdapter(OnOrderClickListener orderClickListener, ArrayList<OrderListView> orderListViews) {
        this.orderClickListener = orderClickListener;
        this.orderListData=orderListViews;
    }



    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_singleview, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        OrderListView orderListDataArray = orderListData.get(position);
//        if(orderListDataArray.getBookTitle()!=null) {
//            holder.bookName.setText(orderListDataArray.getBookTitle());
//        }
        holder.orderID.setText(orderListDataArray.getOrderID());
        if (orderListDataArray.getBookPrice() != null) {
            holder.orderPrice.setText(orderListDataArray.getBookPrice() + "");
        }

//        try {
//            Glide.with(view).load(orderListDataArray.getBookImage()).into(holder.bookImage);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClickListener.onOrderClick(view, holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView getOrderID() {
            return orderID;
        }

        public void setOrderID(TextView orderID) {
            this.orderID = orderID;
        }

        public TextView getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(TextView orderPrice) {
            this.orderPrice = orderPrice;
        }

        TextView orderID;
        TextView orderPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            orderPrice = itemView.findViewById(R.id.orderPrice);

        }
    }
}
//           }








//    // inflates the row layout from xml when needed
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//
//    }

//    // binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.viewButton.setOnClickListener(view -> iClickListener.onItemClick());
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView myTextView;
//        Button viewButton;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            viewButton = itemView.findViewById(R.id.view_btn);
//
//        }
//
//        @Override
//        public void onClick(View view) {
////            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
////        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }


