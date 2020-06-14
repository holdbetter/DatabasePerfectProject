package com.holdbetter.dbperfectproject;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.holdbetter.dbperfectproject.databinding.BookInstanceBinding;
import com.holdbetter.dbperfectproject.gson.GoogleBookInstance;
import com.holdbetter.dbperfectproject.services.BookSetup;

import java.util.ArrayList;
import java.util.List;

public class RecycleNetworkDataAdapter extends RecyclerView.Adapter<RecycleNetworkDataAdapter.BookViewHolder>
        implements BookSetup
{
    private List<?> books = new ArrayList<>();
    private RequestManager glide;

    public RecycleNetworkDataAdapter(RequestManager glide)
    {
        this.glide = glide;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View bookInstance = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_instance, parent, false);

        return new BookViewHolder(BookInstanceBinding.bind(bookInstance));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position)
    {
        GoogleBookInstance bookDataInfo = (GoogleBookInstance) books.get(position);

        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(18))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        if (bookDataInfo.volumeInfo.imageLinks != null)
        {
            glide.load(bookDataInfo.volumeInfo.imageLinks.image_url)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.bookImage);
        }
        else
        {
            glide.load(R.drawable.noise)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.bookImage);
        }

        holder.bookName.setText(bookDataInfo.volumeInfo.title);
        StringBuilder builder = new StringBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            bookDataInfo.volumeInfo.authors.forEach((str) -> builder.append(str).append("\n"));
        }
        else
        {
            for (String author : bookDataInfo.volumeInfo.authors)
            {
                builder.append(author).append("\n");
            }
        }

        holder.bookAuthor.setText(builder.toString());

        holder.bookAuthor.setSingleLine(true);
        holder.bookAuthor.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount()
    {
        return books != null ? books.size() : 0;
    }

    @Override
    public void setBooks(List<?> books)
    {
        this.books = books;
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder
    {
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;
        BookInstanceBinding binding;

        public BookViewHolder(@NonNull BookInstanceBinding binding)
        {
            super(binding.getRoot());

            this.binding = binding;

            bookImage = binding.bookImage;
            bookName = binding.bookName;
            bookAuthor = binding.bookAuthor;
        }
    }
}
