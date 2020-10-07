package com.marcondino.notes;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
// "Model code necessarity to do three things:
// 1. Create a new note;
// 2. Getting all the notes that a user has already created;
// 3. Saving a note to disk.
// To do that, we're going to write a new class called a DAO, it stands for data access object.
// So if you read through the Room documentation, what you'll find is the way that this works is
// we're going to write, not a class, but actually an interface. And we're just going to annotate
// the methods in this interface with some queries. And then, under the hood, the Room library while
// everything is compiling is going to generate some classes for us.

public interface NoteDao {
    // 1. Create a new note;
    @Query("INSERT INTO notes (contents) VALUES ('New note')")
    void create();
    // Wherever this create method is called, this SQL query is going to be run on our database.

    // 2. Getting all the notes that a user has already created;
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    // 3. Saving a note to disk.
    @Query("UPDATE notes SET contents = :contents WHERE id = :id")
    void save(int id, String contents);
}
