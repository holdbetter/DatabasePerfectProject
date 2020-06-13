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
    private int dataRequestCounter = 0;
    private List<LiveData<List<BookDataRequest>>> list = new ArrayList<>();

    public BooksViewModel(@NonNull Application application)
    {
        super(application);
        bookRepository = new BookRepository(application);
        LiveData<List<BookDataRequest>> mBooksDataList1 = bookRepository.mBooksDataList1;
        LiveData<List<BookDataRequest>> mBooksDataList2 = bookRepository.mBooksDataList2;
        LiveData<List<BookDataRequest>> mBooksDataList3 = bookRepository.mBooksDataList3;
        LiveData<List<BookDataRequest>> mBooksDataList4 = bookRepository.mBooksDataList4;
        LiveData<List<BookDataRequest>> mBooksDataList5 = bookRepository.mBooksDataList5;

        list.add(mBooksDataList1);
        list.add(mBooksDataList2);
        list.add(mBooksDataList3);
        list.add(mBooksDataList4);
        list.add(mBooksDataList5);
    }

    public LiveData<List<BookDataRequest>> getBooksDataList()
    {
        LiveData<List<BookDataRequest>> data = list.get(dataRequestCounter);
        dataRequestCounter++;
        if (dataRequestCounter == 5)
            dataRequestCounter = 0;

        return data;
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
