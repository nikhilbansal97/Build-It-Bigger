package com.example.jokesandroidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent receivedIntent = getIntent();
        Bundle extras = receivedIntent.getExtras();
        String joke = extras.getString("joke");

        TextView jokeTextView = findViewById(R.id.jokeTextView);
        jokeTextView.setText(joke);
    }
}
