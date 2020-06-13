package com.holdbetter.dbperfectproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.holdbetter.dbperfectproject.RecyclerMainAdapter;
import com.holdbetter.dbperfectproject.databinding.RecyclerFragmentBinding;
import com.holdbetter.dbperfectproject.viewmodel.BooksViewModel;

public class RecyclerFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        RecyclerFragmentBinding binding = RecyclerFragmentBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.recycle;
        RecyclerMainAdapter adapter = new RecyclerMainAdapter(Glide.with(this));
        binding.setAdapter(adapter);

        BooksViewModel model = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
        model.getBooksDataList().observe(getViewLifecycleOwner(), binding.getAdapter()::setBooks);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        return binding.getRoot();
    }
}
