package com.holdbetter.dbperfectproject;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.holdbetter.dbperfectproject.model.BookDataRequest;

import java.util.ArrayList;
import java.util.List;

public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.BookViewHolder>
{
    private List<BookDataRequest> books = new ArrayList<>();

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
        BookDataRequest bookDataRequest = books.get(position);

        Drawable drawable = holder.itemView.getResources().getDrawable(bookDataRequest.bookImage, null);

        RequestOptions options = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(18));

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(drawable)
                .apply(options)
                .into(holder.bookImage);

        holder.bookName.setText(bookDataRequest.bookTitle);


        String nameAndSurname = String.valueOf(bookDataRequest.authorName.toUpperCase().charAt(0))
                .concat(". ").concat(bookDataRequest.authorSurname);

        SpannableStringBuilder span = new SpannableStringBuilder(nameAndSurname);
        span.setSpan(new RelativeSizeSpan(1.5f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.bookAuthor.setText(span);
        holder.bookAuthor.setSingleLine(true);
        holder.bookAuthor.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount()
    {
        return books != null ? books.size() : 0;
    }

    public void setBooks(List<BookDataRequest> books)
    {
        this.books = books;
        notifyDataSetChanged();
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
