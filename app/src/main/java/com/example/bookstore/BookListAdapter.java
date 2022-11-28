package com.example.bookstore;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    BookListData[] bookListData;
    Context context;

    public BookListAdapter(BookListData[] bookListData, Context activity) {
        this.bookListData = bookListData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_book_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BookListData bookListDataArray = bookListData[position];
        holder.bookName.setText(bookListDataArray.getBookName());
        holder.bookAuthor.setText(bookListDataArray.getBookAuthor());
        holder.bookPrice.setText(bookListDataArray.getBookPrice());
        holder.bookImage.setImageResource(bookListDataArray.getBookImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, bookListDataArray.getBookName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookListData.length;
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
    }
}