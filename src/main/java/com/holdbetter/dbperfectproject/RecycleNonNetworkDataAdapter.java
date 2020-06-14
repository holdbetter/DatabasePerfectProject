package com.holdbetter.dbperfectproject;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.holdbetter.dbperfectproject.databinding.BookInstanceBinding;
import com.holdbetter.dbperfectproject.model.BookDataRequest;
import com.holdbetter.dbperfectproject.services.BookDataInfo;
import com.holdbetter.dbperfectproject.services.BookSetup;

import java.util.ArrayList;
import java.util.List;


public class RecycleNonNetworkDataAdapter extends RecyclerView.Adapter<RecycleNonNetworkDataAdapter.BookViewHolder>
        implements BookSetup
{
    private List<?> books = new ArrayList<>();
    private RequestManager glide;

    public RecycleNonNetworkDataAdapter(RequestManager glide)
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
        BookDataInfo bookDataInfo = (BookDataInfo) books.get(position);

        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(18))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        glide.load(bookDataInfo.bookImage)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.bookImage);

        holder.bookName.setText(bookDataInfo.bookTitle);

        if (bookDataInfo instanceof BookDataRequest)
        {
            BookDataRequest bookDataRequest = (BookDataRequest) bookDataInfo;
            String nameAndSurname = String.valueOf(bookDataRequest.authorName.toUpperCase().charAt(0))
                    .concat(". ").concat(bookDataInfo.authorSurname);
            SpannableStringBuilder span = new SpannableStringBuilder(nameAndSurname);
            span.setSpan(new RelativeSizeSpan(1.5f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.bookAuthor.setText(span);
        }
        else
        {
            holder.bookAuthor.setText(bookDataInfo.authorSurname);
        }

        holder.bookAuthor.setSingleLine(true);
        holder.bookAuthor.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount()
    {
        return books != null ? books.size() : 0;
    }

//    public void setBooks(List<? extends BookDataInfo> books)
//    {
//        this.books = books;
//        notifyDataSetChanged();
//    }

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
