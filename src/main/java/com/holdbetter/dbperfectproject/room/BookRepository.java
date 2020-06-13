package com.holdbetter.dbperfectproject.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.holdbetter.dbperfectproject.R;
import com.holdbetter.dbperfectproject.model.AuthorAndBooks;
import com.holdbetter.dbperfectproject.model.AuthorEntity;
import com.holdbetter.dbperfectproject.model.BookDataRequest;
import com.holdbetter.dbperfectproject.model.BookEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class BookRepository
{
    private BooksDao booksDao;
    public LiveData<List<BookDataRequest>> mBooksDataList1;
    public LiveData<List<BookDataRequest>> mBooksDataList2;
    public LiveData<List<BookDataRequest>> mBooksDataList3;
    public LiveData<List<BookDataRequest>> mBooksDataList4;
    public LiveData<List<BookDataRequest>> mBooksDataList5;

    public BookRepository(Application application)
    {
        BookDatabase database = BookDatabase.getDatabase(application);
        booksDao = database.bookDao();

        mBooksDataList1 = booksDao.getBooksAndAuthor();
        mBooksDataList2 = booksDao.getBooksAndAuthor();
        mBooksDataList3 = booksDao.getBooksAndAuthor();
        mBooksDataList4 = booksDao.getBooksAndAuthor();
        mBooksDataList5 = booksDao.getBooksAndAuthor();
    }

    public Future<List<BookDataRequest>> searchBook(String queryString)
    {
        return BookDatabase.dbExecService.submit(() -> booksDao.search(queryString));
    }

    public void randomInsert()
    {
        BookDatabase.dbExecService.execute(() -> {
            AuthorAndBooks duglas = new AuthorAndBooks();
            duglas.books = new ArrayList<>();

            duglas.author = new AuthorEntity("Дуглас", "Адамс", 1979, -1, "Великобритания");
            duglas.books.add(new BookEntity(7, "Автостопом по галактике", R.drawable.autostop, -1));

            booksDao.insert(duglas);
        });
    }

    public Future<Integer> getBookCount()
    {
        return BookDatabase.dbExecService.submit(() -> booksDao.getBooksCount());
    }
}
