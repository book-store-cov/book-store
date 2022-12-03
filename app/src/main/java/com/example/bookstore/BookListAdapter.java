package com.example.bookstore;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    ArrayList<BookListData> bookListData;
    Context context;
    View view;

    public BookListAdapter(ArrayList<BookListData> bookListData, Context activity) {
        this.bookListData = bookListData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_book_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //final BookListData bookListDataArray = bookListData[position];
        BookListData bookListDataArray = bookListData.get(position);
        if(bookListDataArray.getBookTitle()!=null) {
            holder.bookName.setText(bookListDataArray.getBookTitle());
        }
        holder.bookAuthor.setText(bookListDataArray.getBookAuthor());
        if(bookListDataArray.getBookPrice()!=null) {
            holder.bookPrice.setText(bookListDataArray.getBookPrice()+"");
        }

        try {
            Glide.with(view).load(bookListDataArray.getBookImage()).into(holder.bookImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, bookListDataArray.getBookTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;
        TextView bookPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.imageview);
            bookName = itemView.findViewById(R.id.bookName);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookPrice = itemView.findViewById(R.id.bookPrice);

        }

        public void setBookAuthor(TextView bookAuthor) {
            this.bookAuthor = bookAuthor;
        }

        public void setBookImage(ImageView bookImage) {
            this.bookImage = bookImage;
        }

        public void setBookName(TextView bookName) {
            this.bookName = bookName;
        }

        public void setBookPrice(TextView bookPrice) {
            this.bookPrice = bookPrice;
        }

        public ImageView getBookImage() {
            return bookImage;
        }

        public TextView getBookAuthor() {
            return bookAuthor;
        }

        public TextView getBookName() {
            return bookName;
        }

        public TextView getBookPrice() {
            return bookPrice;
        }
    }
}