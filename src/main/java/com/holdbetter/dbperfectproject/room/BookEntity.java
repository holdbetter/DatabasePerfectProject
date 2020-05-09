package com.holdbetter.dbperfectproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class BookEntity
{
    @PrimaryKey(autoGenerate = true)
    public long bookId;

    @ColumnInfo(name = "title")
    public String name;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "image")
    public int image;

    @ColumnInfo(name = "isbn")
    public long ISBN;

    public BookEntity()
    {

    }

    @Ignore
    public BookEntity(long bookId, String name, String author, int image, long ISBN)
    {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.image = image;
        this.ISBN = ISBN;
    }

    @Ignore
    public BookEntity(String name, String author, int image, long ISBN)
    {
        this.name = name;
        this.author = author;
        this.image = image;
        this.ISBN = ISBN;
    }

    public long getBookId()
    {
        return bookId;
    }

    public void setBookId(long bookId)
    {
        this.bookId = bookId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public int getImage()
    {
        return image;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public long getISBN()
    {
        return ISBN;
    }

    public void setISBN(long ISBN)
    {
        this.ISBN = ISBN;
    }
}
