package com.holdbetter.dbperfectproject;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.holdbetter.dbperfectproject.database.Book;

import java.util.List;

public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.BookViewHolder>
{
    private List<Book> books;

    public RecyclerMainAdapter(List<Book> books)
    {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View bookInstance = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_instance, parent, false);

        return new BookViewHolder(bookInstance);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position)
    {
        Book book = books.get(position);

        Drawable drawable = holder.itemView.getResources().getDrawable(book.getImage(), null);

        RequestOptions options = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(18));

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(drawable)
                .apply(options)
                .into(holder.bookImage);

        holder.bookName.setText(book.getName());
        holder.bookAuthor.setText(book.getAuthor());
    }

    @Override
    public int getItemCount()
    {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder
    {
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;

        public BookViewHolder(@NonNull View v)
        {
            super(v);

            bookImage = v.findViewById(R.id.bookImage);
            bookName = v.findViewById(R.id.bookName);
            bookAuthor = v.findViewById(R.id.bookAuthor);
        }
    }
}
