package com.holdbetter.dbperfectproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "authors")
public class AuthorEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "author_id")
    public long authorId;

    public String name;
    public String surname;
    @ColumnInfo(name = "start_year")
    public int startYear;
    @ColumnInfo(name = "old_year")
    public int oldYear;
    public String country;

    public AuthorEntity()
    {

    }

    @Ignore
    public AuthorEntity(long authorId, String name, String surname, int startYear, int oldYear, String country)
    {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.startYear = startYear;
        this.oldYear = oldYear;
        this.country = country;
    }

    @Ignore
    public AuthorEntity(String name, String surname, int startYear, int oldYear, String country)
    {
        this.name = name;
        this.surname = surname;
        this.startYear = startYear;
        this.oldYear = oldYear;
        this.country = country;
    }
}
