package com.holdbetter.dbperfectproject.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BookEntity.class, AuthorEntity.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase
{
    public abstract BooksDao bookDao();
}
