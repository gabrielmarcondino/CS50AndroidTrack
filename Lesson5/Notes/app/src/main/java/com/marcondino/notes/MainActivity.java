package com.marcondino.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static NoteDatabase database;
    // "The reason I'm making this static is because I want all the different activities in my app,
    // in this case, there are only two, to use the same instance of a database. And, rather than
    // passing that around constructors everywhere, I can just make this static and then other classes
    // in my app can use it."


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"notes")
                .allowMainThreadQueries()
                .build();
        // And this is gonna give us back a database that we can use.

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new NotesAdapter();
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton button = findViewById(R.id.add_note_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.noteDao().create();
                adapter.reload();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
        // OnResume is going to be called every time an activity is brought to the foreground.
    }
}