package com.inf8405.match3app.match3app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
    }

    public void level1_listener(View view) {
        Intent i = new Intent(this, LevelsActivity.class);
        startActivity(i);
    }

    public void level2_listener(View view) {
        Intent i = new Intent(this,LevelsActivity.class);
        startActivity(i);
    }

    public void level3_listener(View view) {
        Intent i = new Intent(this, LevelsActivity.class);
        startActivity(i);
    }

    public void level4_listener(View view) {
        Intent i = new Intent(this, LevelsActivity.class);
        startActivity(i);
    }

    public void level5_listener(View view) {
        Intent i = new Intent(this, LevelsActivity.class);
        startActivity(i);
    }
}
