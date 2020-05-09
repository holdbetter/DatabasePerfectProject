package com.holdbetter.dbperfectproject.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Book implements Parcelable
{
    private long id;
    private String name;
    private String author;
    private int image;
    private long isbn;

    Book(long id, String name, String author, int image, long isbn)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.isbn = isbn;
    }

    Book(String name, String author, int image, long isbn)
    {
        this.name = name;
        this.author = author;
        this.image = image;
        this.isbn = isbn;
    }

    long getId()
    {
        return id;
    }

    void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String _name)
    {
        this.name = _name;
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

    public long getIsbn()
    {
        return isbn;
    }

    public void setIsbn(long isbn)
    {
        this.isbn = isbn;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeLong(image);
        dest.writeLong(isbn);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return id == book.id &&
                image == book.image &&
                isbn == book.isbn &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, author, image, isbn);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>()
    {
        @Override
        public Book createFromParcel(Parcel source)
        {
            long id = source.readLong();
            String name = source.readString();
            String author = source.readString();
            int image = source.readInt();
            long isbn = source.readLong();

            return new Book(id, name, author, image, isbn);
        }

        @Override
        public Book[] newArray(int size)
        {
            return new Book[size];
        }
    };
}
