package com.holdbetter.dbperfectproject.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holdbetter.dbperfectproject.R;
import com.holdbetter.dbperfectproject.RecycleNetworkDataAdapter;
import com.holdbetter.dbperfectproject.RecycleNonNetworkDataAdapter;
import com.holdbetter.dbperfectproject.RecyclerType;
import com.holdbetter.dbperfectproject.databinding.RecyclerFragmentBinding;
import com.holdbetter.dbperfectproject.viewmodel.BooksViewModel;
import com.holdbetter.dbperfectproject.viewmodel.GoogleBooksViewModel;
import com.holdbetter.dbperfectproject.viewmodel.LowLevelBookViewModel;

public class RecyclerFragment extends Fragment
{
    private RecyclerType type;

    public static RecyclerFragment getInstance(RecyclerType type)
    {
        RecyclerFragment fragment = new RecyclerFragment();
        fragment.type = type;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        RecyclerFragmentBinding binding = RecyclerFragmentBinding.inflate(inflater, container, false);

        viewSetup(binding, type);

        return binding.getRoot();
    }

    void viewSetup(RecyclerFragmentBinding binding, RecyclerType type)
    {
        if(type == null) return;

        switch (type)
        {
            default:
                RecycleNonNetworkDataAdapter nonNetworkDataAdapter;
                RecycleNetworkDataAdapter networkDataAdapter;
            case RoomAndLiveData:
                binding.recycleTitle.setText(R.string.roomRecyclerTitle);
                binding.recycleTitleHint.setText(R.string.roomRecyclerTitleHint);
                BooksViewModel roomModel = new ViewModelProvider(requireActivity()).get(BooksViewModel.class);
                nonNetworkDataAdapter = new RecycleNonNetworkDataAdapter(Glide.with(this));
                binding.setAdapter(nonNetworkDataAdapter);
                binding.recycle.setAdapter(nonNetworkDataAdapter);
                roomModel.getBooksDataList().observe(getViewLifecycleOwner(), binding.getAdapter()::setBooks);
                break;
            case SQLiteOpenHelper:
                binding.recycleTitle.setText(R.string.sqliteRecyclerTitle);
                binding.recycleTitleHint.setText(R.string.sqliteRecyclerTitleHint);
                nonNetworkDataAdapter = new RecycleNonNetworkDataAdapter(Glide.with(this));
                binding.setAdapter(nonNetworkDataAdapter);
                binding.recycle.setAdapter(nonNetworkDataAdapter);
                LowLevelBookViewModel lowLevelModel = new ViewModelProvider(requireActivity()).get(LowLevelBookViewModel.class);
                binding.getAdapter().setBooks(lowLevelModel.getBookList());
                break;
            case DownloadingData:
                binding.recycleTitle.setText(R.string.downloadingRecyclerTitle);
                binding.recycleTitleHint.setText(R.string.downloadingRecyclerTitleHint);
                networkDataAdapter = new RecycleNetworkDataAdapter(Glide.with(this));
                binding.setAdapter(networkDataAdapter);
                binding.recycle.setAdapter(networkDataAdapter);
                GoogleBooksViewModel googleBooksModel = new ViewModelProvider(requireActivity()).get(GoogleBooksViewModel.class);

                if (checkConnection(googleBooksModel))
                    googleBooksModel.downloadBooks();

                googleBooksModel.getBookData().observe(getViewLifecycleOwner(),
                        books ->
                        {
                            binding.getAdapter().setBooks(books.googleBookInstances);
                            googleBooksModel.getLoadingState().setValue("ready");
                        });

                googleBooksModel.getLoadingState().observe(getViewLifecycleOwner(),
                        binding.recycleTitleHint::setText);
                break;
        }

        binding.recycle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    // sample by https://github.com/android/connectivity-samples/tree/master/NetworkConnect
    private boolean checkConnection(GoogleBooksViewModel model)
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE))
        {
            model.getLoadingState().setValue("No internet connection");
            return false;
        } else
        {
            return true;
        }
    }
}
