package com.example.lab5_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    //login button activity - grab username and load next screen
    public void loginFunction(View view) {
        //grab the input username and password
        EditText myUsername = (EditText) findViewById(R.id.username);
        String str = myUsername.getText().toString();

        EditText myPassword = (EditText) findViewById(R.id.userPassword);
        String str2 = myPassword.getText().toString();

        //add username to SharePreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();

        //start second activity
        goToActivity2();
    }

    //go to notes screen and send username with
    public void goToActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        //intent.putExtra("message", s);

        startActivity(intent);
    }
    public static String usernameKey = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String usernameKey = "username";
        
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_notes", Context.MODE_PRIVATE);
        
        if(!sharedPreferences.getString(usernameKey, "").equals("")) {
            //"username" key exists in SharedPreferences object which means that a user was logged in before the app closed
            
            //Get the name of that user from SharedPreferences
            String previousUsername = sharedPreferences.getString(usernameKey, "");
            
            //user Intent to start the second activity welcoming the user
            goToActivity2();
        } else {
            //SharedPreferences object has no username key set
            //Start screen 1, that is the main activity
            setContentView(R.layout.activity_main);
        }
    }
}