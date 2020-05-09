package com.holdbetter.dbperfectproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.holdbetter.dbperfectproject.R;
import com.holdbetter.dbperfectproject.RecyclerMainAdapter;
import com.holdbetter.dbperfectproject.database.Book;

import java.util.List;

public class RecyclerFragment extends Fragment
{
    private List<Book> books;

    public RecyclerFragment(List<Book> books)
    {
        this.books = books;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View layoutWithRecyclers = inflater.inflate(R.layout.recycler_fragment, container, false);

        RecyclerView r1 = layoutWithRecyclers.findViewById(R.id.recycle);
        r1.setAdapter(new RecyclerMainAdapter(books));
        r1.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(r1);

        return layoutWithRecyclers;
    }
}
