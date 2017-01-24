package com.inf8405.match3app.match3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start_listener(View view) {
        Intent i = new Intent(this, LevelsActivity.class);
        startActivity(i);
    }

    public void rules_listener(View view) {
        Intent i = new Intent(this, RulesActivity.class);
        startActivity(i);
    }

    public void quit_listener(View view) {
        finish();
    }
}
