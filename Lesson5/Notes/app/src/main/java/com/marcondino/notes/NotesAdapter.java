package com.marcondino.notes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerView;
        TextView textView;

        NoteViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.note_entry);
            textView = view.findViewById(R.id.note_entry_text);
            // Remember we just have to cast this to a note, because this is just a regular object by default.

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note current = (Note) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);

                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_entry, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.textView.setText(current.contents);
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void reload() {
        // Remember we put database as a static field, which means that we can access it by referencing
        // this MainActivity class.
        notes = MainActivity.database.noteDao().getAllNotes();
        notifyDataSetChanged();
    }
}
