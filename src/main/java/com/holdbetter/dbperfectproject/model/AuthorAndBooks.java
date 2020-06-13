package com.holdbetter.dbperfectproject.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.holdbetter.dbperfectproject.model.AuthorEntity;
import com.holdbetter.dbperfectproject.model.BookEntity;

import java.util.List;

public class AuthorAndBooks
{
    @Embedded public AuthorEntity author;
    @Relation(
            parentColumn = "author_id",
            entityColumn = "creator_id"
    )
    public List<BookEntity> books;
}
