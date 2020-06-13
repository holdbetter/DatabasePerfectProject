package com.holdbetter.dbperfectproject.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.holdbetter.dbperfectproject.R;
import com.holdbetter.dbperfectproject.model.AuthorAndBooks;
import com.holdbetter.dbperfectproject.model.AuthorEntity;
import com.holdbetter.dbperfectproject.model.BookEntity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BookEntity.class, AuthorEntity.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase
{
    public abstract BooksDao bookDao();

    private static volatile BookDatabase database;
    static final ExecutorService dbExecService = Executors.newFixedThreadPool(4);

    public static BookDatabase getDatabase(final Context context)
    {
        if (database == null)
        {
            synchronized (BookDatabase.class)
            {
                if (database == null)
                {
                    database = Room.databaseBuilder(context.getApplicationContext(), BookDatabase.class, "books")
                            .addCallback(createCallback)
                            .build();
                }
            }
        }
        return database;
    }

    private static RoomDatabase.Callback createCallback = new Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);

            dbExecService.execute(() -> {
                AuthorAndBooks gluh = new AuthorAndBooks();
                gluh.books = new ArrayList<>();
                AuthorAndBooks harari = new AuthorAndBooks();
                harari.books = new ArrayList<>();
                AuthorAndBooks orwell = new AuthorAndBooks();
                orwell.books = new ArrayList<>();
                AuthorAndBooks aizekson = new AuthorAndBooks();
                aizekson.books = new ArrayList<>();
                AuthorAndBooks dokinz = new AuthorAndBooks();
                dokinz.books = new ArrayList<>();
                AuthorAndBooks mandel = new AuthorAndBooks();
                mandel.books = new ArrayList<>();

                gluh.author = new AuthorEntity("Дмитрий", "Глуховский", 1979, -1, "Россия");
                harari.author = new AuthorEntity("Юваль Ной", "Харари", 1976, -1, "Израиль");
                mandel.author = new AuthorEntity("Осип", "Мандельштам", 1891, 1938, "Польша");
                orwell.author = new AuthorEntity("Джордж", "Оруэлл", 1903, 1950, "Индия");
                aizekson.author = new AuthorEntity("Уолтер", "Айзексон", 1952, -1, "США");
                dokinz.author = new AuthorEntity("Ричард", "Докинз", 1941, -1, "Кения");

                gluh.books.add(new BookEntity(1, "Будущее", R.drawable.future, -1));
                gluh.books.add(new BookEntity(1, "Метро 2033", R.drawable.metro2033, -1));
                gluh.books.add(new BookEntity(1, "Метро 2034", R.drawable.metro2034, -1));
                harari.books.add(new BookEntity(2, "Sapiens. Краткая история человечества", R.drawable.sapiens, -1));
                mandel.books.add(new BookEntity(3, "Я вернулся в мой город", R.drawable.go_back_to_my_town, -1));
                orwell.books.add(new BookEntity(4, "1984", R.drawable.one_nine_eight_four, -1));
                aizekson.books.add(new BookEntity(5, "Стив Джобс", R.drawable.steve_jobs, -1));
                dokinz.books.add(new BookEntity(6, "Эгоистичный ген", R.drawable.egoist, -1));

                BooksDao booksDao = database.bookDao();
                booksDao.insert(gluh);
                booksDao.insert(harari);
                booksDao.insert(mandel);
                booksDao.insert(orwell);
                booksDao.insert(aizekson);
                booksDao.insert(dokinz);
            });

        }
    };
}
