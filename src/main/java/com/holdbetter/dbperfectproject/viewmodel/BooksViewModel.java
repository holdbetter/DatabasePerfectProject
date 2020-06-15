package com.holdbetter.dbperfectproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.holdbetter.dbperfectproject.model.BookDataRequest;
import com.holdbetter.dbperfectproject.room.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BooksViewModel extends AndroidViewModel
{
    private BookRepository bookRepository;
    private LiveData<List<BookDataRequest>> mBooksDataList;

    public BooksViewModel(@NonNull Application application)
    {
        super(application);
        bookRepository = new BookRepository(application);
        mBooksDataList = bookRepository.mBooksDataList;
    }

    public LiveData<List<BookDataRequest>> getBooksDataList()
    {
        return mBooksDataList;
    }

    public List<BookDataRequest> searchBook(String queryString)
    {
        try
        {
            return bookRepository.searchBook(queryString).get();
        } catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void randomInsert()
    {
        bookRepository.randomInsert();
    }
}
