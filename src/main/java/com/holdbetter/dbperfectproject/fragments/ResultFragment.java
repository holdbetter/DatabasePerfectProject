package com.holdbetter.dbperfectproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.holdbetter.dbperfectproject.R;
import com.holdbetter.dbperfectproject.ResultAdapter;
import com.holdbetter.dbperfectproject.database.Book;

import java.util.List;

public class ResultFragment extends Fragment
{
    List<Book> books;

    public ResultFragment(List<Book> books)
    {
        this.books = books;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View resultFragment = inflater.inflate(R.layout.result_fragment, container, false);

        RecyclerView r = resultFragment.findViewById(R.id.recycleResult);
        ResultAdapter adapter = new ResultAdapter(books);
        r.setAdapter(adapter);
        r.setLayoutManager(new LinearLayoutManager(getActivity()));

        return resultFragment;
    }
}
