package com.inf8405.match3app.match3app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
    }

    public void level1_listener(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level","1");
        startActivity(i);
    }

    public void level2_listener(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level","2");
        startActivity(i);
    }

    public void level3_listener(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level","3");
        startActivity(i);
    }

    public void level4_listener(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level","4");
        startActivity(i);
    }

    public void level5_listener(View view) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level","5");
        startActivity(i);
    }
}
