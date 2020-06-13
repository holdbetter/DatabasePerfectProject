package com.holdbetter.dbperfectproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.holdbetter.dbperfectproject.R;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "films.db";
    private static final int DB_VERSION = 1;
    private Context mContext;
    private SQLiteDatabase database;

    public DbHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;

        database = this.getReadableDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DbHelper(@Nullable Context context, @NonNull SQLiteDatabase.OpenParams openParams)
    {
        super(context, DB_NAME, DB_VERSION, openParams);
        this.mContext = context;

        database = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " (" +
                BookContract.BookEntry._ID + " INTEGER PRIMARY KEY, " +
                BookContract.BookEntry.Name + " TEXT NOT NULL," +
                BookContract.BookEntry.Author + " TEXT NOT NULL," +
                BookContract.BookEntry.Image + " INTEGER, " +
                BookContract.BookEntry.ISBN + " INTEGER DEFAULT 0)";

        db.execSQL(SQL_CREATE_TABLE);

        Book futureBook = new Book("Будущее", "Глуховский", R.drawable.future, 0);
        Book sapiensBook = new Book("Sapiens. Краткая история человечества", "Харари", R.drawable.sapiens, 0);
        Book mandelBook = new Book("Я вернулся в мой город", "Мандельштам", R.drawable.go_back_to_my_town, 0);
        Book orwellBook = new Book("1984", "Оруэлл", R.drawable.one_nine_eight_four, 0);
        Book jobsBook = new Book("Стив Джобс", "Айзексон", R.drawable.steve_jobs, 0);
        Book dokinsBook = new Book("Эгоистичный ген", "Докинз", R.drawable.egoist, 0);

        List<Book> initialData = new ArrayList<>();
        initialData.add(futureBook);
        initialData.add(sapiensBook);
        initialData.add(mandelBook);
        initialData.add(orwellBook);
        initialData.add(jobsBook);
        initialData.add(dokinsBook);

        for (Book data : initialData)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BookContract.BookEntry.Name, data.getName());
            contentValues.put(BookContract.BookEntry.Author, data.getAuthor());
            contentValues.put(BookContract.BookEntry.Image, data.getImage());
            contentValues.put(BookContract.BookEntry.ISBN, data.getIsbn());

            db.insert(BookContract.BookEntry.TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public List<Book> getBooks()
    {
        String[] columns = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.Name,
                BookContract.BookEntry.Author,
                BookContract.BookEntry.Image,
                BookContract.BookEntry.ISBN
        };

        Cursor cursor = database.query(BookContract.BookEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);

        List<Book> books = new ArrayList<>();

        while (cursor.moveToNext())
        {
            long id = cursor.getLong(cursor.getColumnIndex(BookContract.BookEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.Name));
            String author = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.Author));
            int image = cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.Image));
            long isbn = cursor.getLong(cursor.getColumnIndex(BookContract.BookEntry.ISBN));

            books.add(new Book(id, name, author, image, isbn));
        }

        cursor.close();
        return books;
    }

    public List<Book> search(String searchQuery)
    {
        List<Book> results = new ArrayList<>();

        String[] columns =
                {
                        BookContract.BookEntry.Name,
                        BookContract.BookEntry.Author,
                        BookContract.BookEntry.Image,
                        BookContract.BookEntry.ISBN
                };

        String[] words = searchQuery.trim().split(" ", 2);
        String firstWord = words[0];
        firstWord = "%" + firstWord + "%";

        String whereClause = BookContract.BookEntry.Name + " LIKE ?" +
                " OR " + BookContract.BookEntry.Author + " LIKE ?";
        String[] whereArgs = {firstWord, firstWord};

        Cursor cursor = database.query(BookContract.BookEntry.TABLE_NAME,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        while (cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.Name));
            String author = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.Author));
            int image = cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.Image));
            long isbn = cursor.getLong(cursor.getColumnIndex(BookContract.BookEntry.ISBN));

            results.add(new Book(name, author, image, isbn));
        }

        cursor.close();

        return results;
    }


//    public static class DbImageHelper
//    {
//        public static byte[] getBytes(Bitmap bitmap)
//        {
//            byte[] result = null;
//
//            try (ByteArrayOutputStream stream = new ByteArrayOutputStream())
//            {
//                bitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);
//                result = new byte[stream.size()];
//                result = stream.toByteArray();
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        public static Bitmap getImage(byte[] image)
//        {
//            return BitmapFactory.decodeByteArray(image, 0, image.length);
//        }
//    }

    @Override
    public synchronized void close()
    {
        if (database != null)
        {
            database.close();
        }
        super.close();
    }
}
