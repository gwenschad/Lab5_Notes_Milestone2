package com.example.lab5_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //login button activity - grab username and load next screen
    public void loginFunction(View view) {
        //grab the input username
        EditText myUsername = (EditText) findViewById(R.id.username);
        String str = myUsername.getText().toString();

        goToActivity2(str);
    }

    //go to notes screen and send username with
    public void goToActivity2(String s) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", s);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}