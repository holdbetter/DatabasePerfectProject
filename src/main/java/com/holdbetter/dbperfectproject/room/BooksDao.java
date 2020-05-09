package com.holdbetter.dbperfectproject.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class BooksDao
{
    @Query("SELECT * FROM books")
    public abstract List<BookEntity> getBooks();

    @Query("SELECT * FROM authors")
    public abstract List<AuthorEntity> getAuthors();

    @Transaction
    @Query("SELECT * FROM authors")
    public abstract List<AuthorAndBooks> getAuthorAndBooks();

    @Query("SELECT title as bookTitle, " +
            "image as bookImage, " +
            "name as authorName, " +
            "surname as authorSurname " +
            "FROM books " +
            "INNER JOIN authors ON author_id = creator_id ")
    public abstract List<BookDataRequest> getBooksAndAuthor();

    @Query("SELECT title as bookTitle, " +
            "image as bookImage, " +
            "name as authorName, " +
            "surname as authorSurname " +
            "FROM books " +
            "INNER JOIN authors ON author_id = creator_id " +
            "WHERE title LIKE :queryString OR " +
            "name LIKE :queryString")
    public abstract List<BookDataRequest> search(String queryString);

    @Transaction
    public void insert(AuthorAndBooks authorAndBook)
    {
        insertAuthors(authorAndBook.author);
        for (BookEntity book : authorAndBook.books)
        {
            insertBooks(book);
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertBooks(BookEntity... books);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertAuthors(AuthorEntity... authors);

    @Query("SELECT COUNT(*) FROM books")
    public abstract int getBooksCount();

    @Query("SELECT COUNT(*) FROM authors")
    abstract int getAuthorsCount();
}
