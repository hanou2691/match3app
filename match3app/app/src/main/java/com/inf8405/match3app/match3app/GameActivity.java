package com.inf8405.match3app.match3app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class GameActivity extends AppCompatActivity {

    public static int color_brown;
    public static int color_green;
    public static int color_yellow;
    public static int color_indigo;
    public static int color_blue;
    public static int color_red;

    public static int[] colors;

    public LevelConfig levelConfig;

    public int nbMovesLeft;
    public static int totalScore;

    public TextView scoreTV;
    public TextView movesLeftTV;
    public TextView targetScoreTV;

    int comboCounter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load colors from colors.xml
        loadColors();

        // Get selected level and its attributes
        Intent i = getIntent();
        levelConfig = (LevelConfig) i.getSerializableExtra("level_config");

        // Load selected level colors
        switch (levelConfig.level){
            case 1: loadLevelOne();
                break;
            case 2: loadLevelTwo();
                break;
            case 3: loadLevelThree();
                break;
            case 4: loadLevelFour();
                break;
            default:
                loadLevelOne();
        }

        // Initiate the score
        totalScore = 0;

        // Create the game view
        setContentView(R.layout.activity_game);

        initializeViews();
    }

    private void loadColors() {
        color_brown = ResourcesCompat.getColor(getResources(),R.color.colorBrown, null);
        color_green = ResourcesCompat.getColor(getResources(),R.color.colorGreen, null);
        color_yellow = ResourcesCompat.getColor(getResources(),R.color.colorYellow, null);
        color_indigo = ResourcesCompat.getColor(getResources(),R.color.colorIndigo, null);
        color_blue = ResourcesCompat.getColor(getResources(),R.color.colorBlue, null);
        color_red = ResourcesCompat.getColor(getResources(),R.color.colorRed, null);
    }

    private void loadLevelOne() {
        colors = new int[]{
            color_yellow,color_green,color_green,color_yellow,color_green,color_yellow,color_indigo,color_indigo,
            color_green,color_indigo,color_brown,color_red,color_blue,color_blue,color_brown,color_blue,
            color_blue,color_red,color_brown,color_red,color_blue,color_green,color_brown,color_red,
            color_indigo,color_brown,color_indigo,color_blue,color_brown,color_green,color_green,color_brown,
            color_yellow,color_red,color_yellow,color_brown,color_blue,color_red,color_green,color_indigo
        };
    }

    private void loadLevelTwo() {
        colors = new int[]{
            color_red,color_green,color_green,color_brown,color_red,color_blue,color_green,color_green,
            color_brown,color_red,color_brown,color_red,color_blue,color_blue,color_indigo,color_green,
            color_indigo,color_blue,color_green,color_brown,color_blue,color_green,color_indigo,color_brown,
            color_red,color_yellow,color_indigo,color_brown,color_yellow,color_indigo,color_green,color_brown,
            color_brown,color_blue,color_red,color_yellow,color_green,color_indigo,color_green,color_indigo,
            color_red,color_yellow,color_yellow,color_blue,color_brown,color_yellow,color_blue,color_blue
        };
    }

    private void loadLevelThree() {
        colors = new int[]{
            color_indigo,color_brown,color_brown,color_green,color_red,color_green,color_green,
            color_indigo,color_indigo,color_brown,color_indigo,color_green,color_red,color_brown,
            color_red,color_green,color_blue,color_red,color_brown,color_green,color_blue,
            color_red,color_red,color_indigo,color_blue,color_indigo,color_red,color_indigo,
            color_green,color_indigo,color_blue,color_red,color_indigo,color_blue,color_green,
            color_red,color_green,color_red,color_blue,color_brown,color_red,color_green,
            color_blue,color_green,color_red,color_red,color_green,color_blue,color_blue
        };
    }

    private void loadLevelFour() {
        colors = new int[]{
            color_red,color_brown,color_brown,color_red,color_green,color_brown,color_green,
            color_indigo,color_red,color_blue,color_indigo,color_blue,color_blue,color_green,
            color_blue,color_indigo,color_red,color_brown,color_brown,color_indigo,color_indigo,
            color_green,color_indigo,color_brown,color_red,color_indigo,color_indigo,color_blue,
            color_green,color_brown,color_blue,color_indigo,color_blue,color_red,color_brown,
            color_blue,color_indigo,color_blue,color_blue,color_green,color_green,color_brown,color_red,
            color_green,color_green,color_brown,color_indigo,color_green,color_red,color_green,
            color_blue,color_brown,color_blue,color_brown,color_blue,color_indigo,color_brown
        };
    }

    public void messageEndLevel(String message ,final String title){
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setNegativeButton("Cancel",null);
        dlgAlert.setCancelable(true);
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(title == "Success"){
                            loadNextLevel();
                        }
                        else {
                            initializeGrid();
                            initializeViews();
                        }
                    }
                });
        dlgAlert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dlgAlert.create().show();
    }

    public void quit_handler(View view) {
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Do you really want to quit the game?");
        dlgAlert.setTitle("Quit");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setNegativeButton("Cancel",null);
        dlgAlert.setCancelable(true);
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        dlgAlert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dlgAlert.create().show();
    }

    public void joker_handler(View view) {
        initializeGrid();
    }

    public void initializeViews() {
        // Get views
        scoreTV = (TextView) findViewById(R.id.edit_score);
        movesLeftTV = (TextView) findViewById(R.id.edit_moves_left);
        targetScoreTV = (TextView) findViewById(R.id.edit_target);

        scoreTV.setText(String.valueOf(0));
        movesLeftTV.setText(String.valueOf(levelConfig.initialNbMoves));
        targetScoreTV.setText(String.valueOf(levelConfig.targetScore));

        totalScore = 0;
        nbMovesLeft = levelConfig.initialNbMoves;
    }

    public void updateViews() {
        scoreTV.setText(String.valueOf(totalScore));
        movesLeftTV.setText(String.valueOf(nbMovesLeft));
    }

    public void calculateScore(int matchCount) {
        comboCounter = 2;
        switch(matchCount){
            case 3:
                totalScore += 100;
                break;
            case 4:
                totalScore += 200;
                break;
            case 5:
                totalScore += 300;
                break;
        }

        // Decrement remaining moves
        nbMovesLeft--;

        if (nbMovesLeft == 0) {
            if (totalScore >= levelConfig.targetScore) {
                // Load next level
                messageEndLevel("Congratulations! Do you want to move to the next level?", "Success");
            }
            else {
                // Game over
                messageEndLevel("Sorry! Do you want to restart the level?", "Failure");
            }
        }

        // Update score and moves views
        updateViews();
    }

    public void calculateComboScore(int matchCount) {
        switch(matchCount){
            case 3:
                totalScore += 100 * comboCounter;
                break;
            case 4:
                totalScore += 200 * comboCounter;
                break;
            case 5:
                totalScore += 300 * comboCounter;
                break;
        }

        comboCounter++;

        // Update score and moves views
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateViews();
            }
        });
    }


    public void loadNextLevel() {
        if (++levelConfig.level < 5){
            // Edit level state
            ((Globals)getApplication()).locks[levelConfig.level - 2] = false;

            // Start activity
            Intent i = new Intent(this, GameActivity.class);
            InputStream is = getResources().openRawResource(R.raw.levels_configuration);
            try {
                // Stop drawing preview grid
                GameView view = (GameView) findViewById(R.id.game_view);
                view.setWillNotDraw(true);
                view.comboRunnable.pauseThread();
                view.comboThread.interrupt();

                // Load new activity
                LevelConfig nextLevelConfig = LevelsActivity.readLevelConfig(is, levelConfig.level);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("level_config", nextLevelConfig);
                startActivity(i);
                finish();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        else {
            // The game is over
            Toast.makeText(this, "congratulations!", Toast.LENGTH_SHORT).show();
        }
    }

    public void initializeGrid(){
        switch(levelConfig.level){
            case 1:
                loadLevelOne();
                break;
            case 2:
                loadLevelTwo();
                break;
            case 3:
                loadLevelThree();
                break;
            case 4:
                loadLevelFour();
                break;
            default:
                loadLevelOne();
        }

        // Reset circle matrix in GameView
        GameView view = (GameView) findViewById(R.id.game_view);
        view.resetCircleMatrix();
    }

    public void restart_handler(View view) {
        initializeGrid();
        initializeViews();
    }
}
