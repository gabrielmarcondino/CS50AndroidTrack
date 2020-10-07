package com.marcondino.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
// "Entity annotation: this says that this class represents a table in my database. So this will create a SQLite
// table called the notes with two columns, a primary key integer autoincrement column called id and a string
// column, which corresponds to a text column in SQLite3, called contents."
public class Note {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "contents")
    public String contents;
}
