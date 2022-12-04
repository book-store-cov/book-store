package com.example.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.cart.CartData;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
IClickListener iClickListener;

    private ArrayList<CartData> cartLists;
    int count = 1;


    public CartAdapter(IClickListener iClickListener, ArrayList<CartData> cartList) {
        cartLists = cartList;
        this.iClickListener=iClickListener;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_singleview, parent, false);
        return new ViewHolder(view);


    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemNAme.setText(cartLists.get(position).getTitle());
        holder.itemID.setText("" + cartLists.get(position).getISBN());
        holder.price.setText("1 x " + cartLists.get(position).getPrice());
        holder.bookFinalPrice.setText("" + cartLists.get(position).getPrice());
        holder.count.setText("1");

        holder.incrementButton.setOnClickListener(view -> {
            count = cartLists.get(position).getCount();
            count++;
            cartLists.get(position).setCount(count);
            holder.count.setText("" + count);
            holder.price.setText(count +" x " + cartLists.get(holder.getAdapterPosition()).getPrice());
            int price = count * cartLists.get(holder.getAdapterPosition()).getPrice();
            holder.bookFinalPrice.setText(""+price);
            iClickListener.onIncrementClick(cartLists.get(holder.getAdapterPosition()).getPrice());
        });
        holder.decrementButton.setOnClickListener(view -> {
            if (count >= 1) {
                count = cartLists.get(position).getCount();
                count--;
                cartLists.get(position).setCount(count);
                holder.count.setText("" + count);
                holder.price.setText(count +" x " + cartLists.get(holder.getAdapterPosition()).getPrice());
                holder.bookFinalPrice.setText(""+count * cartLists.get(holder.getAdapterPosition()).getPrice());
                iClickListener.onDecrementClick(cartLists.get(holder.getAdapterPosition()).getPrice());
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return cartLists.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemNAme, itemID, price, count, bookFinalPrice;
        AppCompatButton incrementButton, decrementButton;

        ViewHolder(View itemView) {
            super(itemView);
            itemNAme = itemView.findViewById(R.id.bookName);
            itemID = itemView.findViewById(R.id.bookId);
            price = itemView.findViewById(R.id.qtySellingPrice);
            count = itemView.findViewById(R.id.qtyText);
            bookFinalPrice = itemView.findViewById(R.id.itemFinalPrice);
            incrementButton = itemView.findViewById(R.id.increment_btn);
            decrementButton = itemView.findViewById(R.id.decrement_btn);

//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
//    String getItem(int id) {
////        return mData.get(id);
//    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

