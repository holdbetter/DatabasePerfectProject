package com.holdbetter.dbperfectproject.lowleveldbaccess;

import android.os.Parcel;
import android.os.Parcelable;

import com.holdbetter.dbperfectproject.services.BookDataInfo;

import java.util.Objects;

public class LowLevelBook extends BookDataInfo implements Parcelable
{
    public long isbn;

    LowLevelBook(long id, String bookTitle, String authorSurname, int bookImage, long isbn)
    {
        this.id = id;
        this.bookTitle = bookTitle;
        this.authorSurname = authorSurname;
        this.bookImage = bookImage;
        this.isbn = isbn;
    }

    LowLevelBook(String bookTitle, String authorSurname, int bookImage, long isbn)
    {
        this.bookTitle = bookTitle;
        this.authorSurname = authorSurname;
        this.bookImage = bookImage;
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
        dest.writeString(bookTitle);
        dest.writeString(authorSurname);
        dest.writeLong(bookImage);
        dest.writeLong(isbn);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LowLevelBook book = (LowLevelBook) o;
        return id == book.id &&
                bookImage == book.bookImage &&
                isbn == book.isbn &&
                Objects.equals(bookTitle, book.bookTitle) &&
                Objects.equals(authorSurname, book.authorSurname);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, bookTitle, authorSurname, bookImage, isbn);
    }

    public static final Parcelable.Creator<LowLevelBook> CREATOR = new Creator<LowLevelBook>()
    {
        @Override
        public LowLevelBook createFromParcel(Parcel source)
        {
            long id = source.readLong();
            String name = source.readString();
            String author = source.readString();
            int image = source.readInt();
            long isbn = source.readLong();

            return new LowLevelBook(id, name, author, image, isbn);
        }

        @Override
        public LowLevelBook[] newArray(int size)
        {
            return new LowLevelBook[size];
        }
    };
}
