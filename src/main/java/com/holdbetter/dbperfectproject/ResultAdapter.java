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

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.holdbetter.dbperfectproject.databinding.ResultInstanceBinding;
import com.holdbetter.dbperfectproject.model.BookDataRequest;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.BookViewHolder>
{
    private RequestManager glide;
    private List<BookDataRequest> books = new ArrayList<>();

    public ResultAdapter(List<BookDataRequest> books, RequestManager glide)
    {
        this.books = books;
        this.glide = glide;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View bookInstance = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_instance, parent, false);

        return new BookViewHolder(ResultInstanceBinding.bind(bookInstance));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position)
    {
        BookDataRequest bookDataRequest = books.get(position);

        glide.load(bookDataRequest.bookImage)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
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
        return books != null ? books.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder
    {
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;

        ResultInstanceBinding binding;

        public BookViewHolder(@NonNull ResultInstanceBinding binding)
        {
            super(binding.getRoot());

            this.binding = binding;

            bookImage = binding.bookImage;
            bookName = binding.bookName;
            bookAuthor = binding.bookAuthor;
        }
    }
}
