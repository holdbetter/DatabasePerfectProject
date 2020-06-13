package com.holdbetter.dbperfectproject;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.holdbetter.dbperfectproject.databinding.BookInstanceBinding;
import com.holdbetter.dbperfectproject.model.BookDataRequest;

import java.util.ArrayList;
import java.util.List;


public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.BookViewHolder>
{
    private List<BookDataRequest> books = new ArrayList<>();
    private RequestManager glide;

    public RecyclerMainAdapter(RequestManager glide)
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
        BookDataRequest bookDataRequest = books.get(position);

        // first item margin setup
//        if(position == 0) {
//            LinearLayout root = holder.binding.getRoot();
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
//
//            int px = (int) (20 * Resources.getSystem().getDisplayMetrics().density);
//
//            params.setMarginStart(px);
//            root.setLayoutParams(params);
//        }

        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(18))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        glide.load(bookDataRequest.bookImage)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
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
