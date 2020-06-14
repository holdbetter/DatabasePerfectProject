package com.holdbetter.dbperfectproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.holdbetter.dbperfectproject.lowleveldbaccess.DbHelper;
import com.holdbetter.dbperfectproject.lowleveldbaccess.LowLevelBook;

import java.util.List;

public class LowLevelBookViewModel extends AndroidViewModel
{
    private List<LowLevelBook> bookList;

    public LowLevelBookViewModel(@NonNull Application application)
    {
        super(application);

        DbHelper dbHelper = new DbHelper(application);
        bookList = dbHelper.getBooks();
    }

    public List<LowLevelBook> getBookList()
    {
        return bookList;
    }

    public void setBookList(List<LowLevelBook> bookList)
    {
        this.bookList = bookList;
    }
}
