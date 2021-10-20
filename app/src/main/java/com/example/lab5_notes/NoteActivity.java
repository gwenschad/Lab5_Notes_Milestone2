package com.example.lab5_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    int noteid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //get EditText view
        EditText userNote = (EditText) findViewById(R.id.textNote);
        //String noteContent = userNote.getText().toString();

        //get Intent
        Intent intent = getIntent();

        //get the value of integer "noteid" from intent
        Integer noteIdIntent = intent.getIntExtra("noteid", -1);
        //initialize class variable "noteid" with the value from intent
        noteid = noteIdIntent;

        if(noteid != -1) {
            //display content of note by retrieving "notes" ArrayList in MainActivity2
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();

            //use editText.setText() to display the contents of this note on the screen
            userNote.setText(noteContent);
        }
    }

    //save note when save button is clicked
    public void saveFunction(View view) {
        //get editText view and the content that user entered
        EditText userNote = (EditText) findViewById(R.id.textNote);
        String content = userNote.getText().toString();

        //Initialize SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        //Initialize DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);


        //set username in the following variable by fetching it from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("com.example.lab5_notes", Context.MODE_PRIVATE);
        String username = prefs.getString(MainActivity.usernameKey, "");

        //save information to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        if(noteid == -1) { //add note
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { //update note
            title = "NOTE_" + (noteid + 1);
            //Log.i("new content", content);
            dbHelper.updateNote(title, date, content, username);
        }

        //go to main activity 2
        Intent intent = new Intent(this, MainActivity2.class);
        //intent.putExtra("noteid", -1);
        startActivity(intent);

    }
}