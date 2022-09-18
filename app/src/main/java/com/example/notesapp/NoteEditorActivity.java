package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        // Displaying info
        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
//       spelling should be same as the one in main activity        put default value -1 as arrays can start from 0

        if(noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        }else{
            // addressing the menu error when we add notes, "the array out of bounds" error
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;  // as array size is starting from 0
        }

        // Retaining the notes info when updating it

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    // work here
                    // updating the array
                    MainActivity.notes.set(noteId, String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                    // Saving the notes
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

    }
}