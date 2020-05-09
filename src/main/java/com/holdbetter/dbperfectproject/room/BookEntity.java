package com.holdbetter.dbperfectproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class BookEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    public long bookId;
    @ColumnInfo(name = "creator_id")
    public long authorCreatorId;
    @ColumnInfo(name = "title")
    public String name;
    @ColumnInfo(name = "image")
    public int image;
    @ColumnInfo(name = "isbn")
    public long ISBN;

    public BookEntity()
    {

    }

    @Ignore
    public BookEntity(long authorCreatorId, String name, int image, long ISBN)
    {
        this.authorCreatorId = authorCreatorId;
        this.name = name;
        this.image = image;
        this.ISBN = ISBN;
    }

    @Ignore
    public BookEntity(long bookId, long authorCreatorId, String name, int image, long ISBN)
    {
        this.bookId = bookId;
        this.authorCreatorId = authorCreatorId;
        this.name = name;
        this.image = image;
        this.ISBN = ISBN;
    }
}
