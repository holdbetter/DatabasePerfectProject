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
    private LiveData<List<BookDataRequest>> mBooksDataList1;
    private LiveData<List<BookDataRequest>> mBooksDataList2;
    private LiveData<List<BookDataRequest>> mBooksDataList3;
    private LiveData<List<BookDataRequest>> mBooksDataList4;
    private LiveData<List<BookDataRequest>> mBooksDataList5;
    private LiveData<List<BookDataRequest>> mBooksDataList6;
    private LiveData<List<BookDataRequest>> searchResult;
    private BookRepository bookRepository;
    private int dataRequestCounter = 0;
    private List<LiveData<List<BookDataRequest>>> list = new ArrayList<>();

    public BooksViewModel(@NonNull Application application)
    {
        super(application);
        bookRepository = new BookRepository(application);
        mBooksDataList1 = bookRepository.mBooksDataList1;
        mBooksDataList2 = bookRepository.mBooksDataList2;
        mBooksDataList3 = bookRepository.mBooksDataList3;
        mBooksDataList4 = bookRepository.mBooksDataList4;
        mBooksDataList5 = bookRepository.mBooksDataList5;

        list.add(mBooksDataList1);
        list.add(mBooksDataList2);
        list.add(mBooksDataList3);
        list.add(mBooksDataList4);
        list.add(mBooksDataList5);
    }

    public LiveData<List<BookDataRequest>> getmBooksDataList1()
    {
        return mBooksDataList1;
    }

    public LiveData<List<BookDataRequest>> getBooksDataList()
    {
        LiveData<List<BookDataRequest>> data = list.get(dataRequestCounter);
        dataRequestCounter++;
        if (dataRequestCounter == 5)
            dataRequestCounter = 0;

        return data;
    }

    public LiveData<List<BookDataRequest>> searchBook(String queryString)
    {
        try
        {
            searchResult = bookRepository.searchBook(queryString).get();
            return searchResult;
        } catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        return null;
    }

//    public void welcomeInsert()
//    {
//        bookRepository.welcomeInsert();
//    }

    public void randomInsert()
    {
        bookRepository.randomInsert();
    }
}
