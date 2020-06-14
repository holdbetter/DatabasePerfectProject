package com.holdbetter.dbperfectproject.lowleveldbaccess;

import android.provider.BaseColumns;

public class BookContract
{
    private BookContract()
    {

    }

    static class BookEntry implements BaseColumns
    {
        public static String TABLE_NAME = "Books";

        public static final String _ID = BaseColumns._ID;
        public static final String Name = "name";
        public static final String Author = "author";
        public static final String Image = "image";
        public static final String ISBN = "isbn";
    }
}
