package com.example.lab5_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        welcomeMessage.setText("Welcome " +str +"!");
    }
}