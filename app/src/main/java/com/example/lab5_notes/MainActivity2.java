package com.example.lab5_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    TextView welcomeMessage;

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //display welcome message. Fetch username from SharedPreferences
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        SharedPreferences prefs = getSharedPreferences("com.example.lab5_notes", Context.MODE_PRIVATE);
        String username = prefs.getString(MainActivity.usernameKey, "");
        //Intent intent = getIntent();
        //String str = intent.getStringExtra("message");
        welcomeMessage.setText("Welcome " +username +"!");

        //get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        //Initiate the "notes" class variable using readNotes method implemented in DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        //notes = new ArrayList<>();
        notes = dbHelper.readNotes(username);

        //create an ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        //user ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        //add onItemClickListener for ListView item, a note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //initialize intent to take user to third activity (NoteActivity)
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                //Add the position of the item that was clicked on as "noteid"
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.logout) {
            //Erase username from shared preferences
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_notes", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.addNote) {
            //go to activity 3
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("noteid", -1);
            startActivity(intent);

            return true;
        }
        return true;
    }
}