package com.holdbetter.dbperfectproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holdbetter.dbperfectproject.R;
import com.holdbetter.dbperfectproject.ResultAdapter;
import com.holdbetter.dbperfectproject.model.BookDataRequest;
import com.holdbetter.dbperfectproject.viewmodel.BooksViewModel;

import java.util.List;

public class ResultFragment extends Fragment
{
    private String query;

    public ResultFragment(String queryString)
    {
        this.query = queryString;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View resultFragment = inflater.inflate(R.layout.result_fragment, container, false);

        BooksViewModel model = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);

        RecyclerView recycler = resultFragment.findViewById(R.id.recycleResult);
        ResultAdapter adapter = new ResultAdapter(model.searchBook(query), Glide.with(this));
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return resultFragment;
    }
}
