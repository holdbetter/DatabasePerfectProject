package com.holdbetter.dbperfectproject.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
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
