package com.holdbetter.dbperfectproject.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao
{
    @Query("SELECT * FROM books")
    List<BookEntity> getBooks();

    @Query("SELECT * FROM books " +
            "WHERE title LIKE :queryString OR " +
            "author LIKE :queryString")
    List<BookEntity> search(String queryString);

    @Insert
    void insertAll(BookEntity... books);

    @Query("SELECT COUNT(*) FROM books")
    int getBooksCount();
}
