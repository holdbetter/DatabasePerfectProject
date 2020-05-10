package com.holdbetter.dbperfectproject;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holdbetter.dbperfectproject.room.BookDataRequest;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.BookViewHolder>
{
    private List<BookDataRequest> books;

    public ResultAdapter(List<BookDataRequest> books)
    {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View bookInstance = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_instance, parent, false);

        return new BookViewHolder(bookInstance);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position)
    {
        BookDataRequest bookDataRequest = books.get(position);

        Drawable imageBitmap = holder.itemView.getResources().getDrawable(bookDataRequest.bookImage, null);

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(imageBitmap)
                .circleCrop()
                .into(holder.bookImage);

        holder.bookName.setText(bookDataRequest.bookTitle);

        String nameAndSurname = String.valueOf(bookDataRequest.authorName.toUpperCase().charAt(0))
                .concat(". ").concat(bookDataRequest.authorSurname);

        SpannableStringBuilder span = new SpannableStringBuilder(nameAndSurname);
        span.setSpan(new RelativeSizeSpan(1.5f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.bookAuthor.setText(span);
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
