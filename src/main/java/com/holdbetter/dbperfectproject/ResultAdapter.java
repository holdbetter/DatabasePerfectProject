package com.holdbetter.dbperfectproject;

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
import com.holdbetter.dbperfectproject.databinding.EmptyInstanceBinding;
import com.holdbetter.dbperfectproject.databinding.ResultInstanceBinding;
import com.holdbetter.dbperfectproject.model.BookDataRequest;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int EMPTY_INSTANCE = -1;
    private static final int RESULT = 1;

    private RequestManager glide;
    private List<BookDataRequest> books;

    public ResultAdapter(List<BookDataRequest> books, RequestManager glide)
    {
        this.books = books;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (viewType == EMPTY_INSTANCE)
        {
            View emptyInstance = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.empty_instance, parent, false);
            return new EmptyViewHolder(EmptyInstanceBinding.bind(emptyInstance));
        }
        else
        {
            View bookInstance = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.result_instance, parent, false);
            return new BookViewHolder(ResultInstanceBinding.bind(bookInstance));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (getItemViewType(position) == EMPTY_INSTANCE)
        {
            return;
        }

        BookViewHolder bookViewHolder = (BookViewHolder) holder;

        BookDataRequest bookDataRequest = books.get(position);

        glide.load(bookDataRequest.bookImage)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(bookViewHolder.bookImage);

        bookViewHolder.bookName.setText(bookDataRequest.bookTitle);

        String nameAndSurname = String.valueOf(bookDataRequest.authorName.toUpperCase().charAt(0))
                .concat(". ").concat(bookDataRequest.authorSurname);

        SpannableStringBuilder span = new SpannableStringBuilder(nameAndSurname);
        span.setSpan(new RelativeSizeSpan(1.5f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bookViewHolder.bookAuthor.setText(span);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (books.size() == 0)
        {
            return EMPTY_INSTANCE;
        }
        else
        {
            return RESULT;
        }
    }

    @Override
    public int getItemCount()
    {
        return books.size() != 0 ? books.size() : 1;
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

    public static class EmptyViewHolder extends RecyclerView.ViewHolder
    {
        public EmptyViewHolder(@NonNull EmptyInstanceBinding binding)
        {
            super(binding.getRoot());
        }
    }
}
