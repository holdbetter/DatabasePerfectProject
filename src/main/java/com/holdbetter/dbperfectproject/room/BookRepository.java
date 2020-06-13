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

    public Future<LiveData<List<BookDataRequest>>> searchBook(String queryString)
    {
        return BookDatabase.dbExecService.submit(() -> booksDao.search(queryString));
    }

//    public void welcomeInsert()
//    {
//        BookDatabase.dbExecService.execute(() ->
//        {
//            AuthorAndBooks gluh = new AuthorAndBooks();
//            gluh.books = new ArrayList<>();
//            AuthorAndBooks harari = new AuthorAndBooks();
//            harari.books = new ArrayList<>();
//            AuthorAndBooks orwell = new AuthorAndBooks();
//            orwell.books = new ArrayList<>();
//            AuthorAndBooks aizekson = new AuthorAndBooks();
//            aizekson.books = new ArrayList<>();
//            AuthorAndBooks dokinz = new AuthorAndBooks();
//            dokinz.books = new ArrayList<>();
//            AuthorAndBooks mandel = new AuthorAndBooks();
//            mandel.books = new ArrayList<>();
//
//            gluh.author = new AuthorEntity("Дмитрий", "Глуховский", 1979, -1, "Россия");
//            harari.author = new AuthorEntity("Юваль Ной", "Харари", 1976, -1, "Израиль");
//            mandel.author = new AuthorEntity("Осип", "Мандельштам", 1891, 1938, "Польша");
//            orwell.author = new AuthorEntity("Джордж", "Оруэлл", 1903, 1950, "Индия");
//            aizekson.author = new AuthorEntity("Уолтер", "Айзексон", 1952, -1, "США");
//            dokinz.author = new AuthorEntity("Ричард", "Докинз", 1941, -1, "Кения");
//
//            gluh.books.add(new BookEntity(1, "Будущее", R.drawable.future, -1));
//            gluh.books.add(new BookEntity(1, "Метро 2033", R.drawable.metro2033, -1));
//            gluh.books.add(new BookEntity(1, "Метро 2034", R.drawable.metro2034, -1));
//            harari.books.add(new BookEntity(2, "Sapiens. Краткая история человечества", R.drawable.sapiens, -1));
//            mandel.books.add(new BookEntity(3, "Я вернулся в мой город", R.drawable.go_back_to_my_town, -1));
//            orwell.books.add(new BookEntity(4, "1984", R.drawable.one_nine_eight_four, -1));
//            aizekson.books.add(new BookEntity(5, "Стив Джобс", R.drawable.steve_jobs, -1));
//            dokinz.books.add(new BookEntity(6, "Эгоистичный ген", R.drawable.egoist, -1));
//
//            booksDao.insert(gluh);
//            booksDao.insert(harari);
//            booksDao.insert(mandel);
//            booksDao.insert(orwell);
//            booksDao.insert(aizekson);
//            booksDao.insert(dokinz);
//        });
//    }

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
