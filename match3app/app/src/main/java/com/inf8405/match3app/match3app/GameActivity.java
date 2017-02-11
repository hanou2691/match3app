package com.inf8405.match3app.match3app;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
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

    public String level;
    public int nbMoves;
    public int targetScore;
    public int x;
    public int y;
    public static int totalScore;

    public TextView scoreTV;
    public TextView movesLeftTV;
    public TextView targetScoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load colors from colors.xml
        loadColors();

        // Get selected level
        Intent i = getIntent();
        level = i.getStringExtra("level");

        // Load selected level colors
        switch (level){
            case "1": loadLevelOne();
                break;
            case "2": loadLevelTwo();
                break;
            case "3": loadLevelThree();
                break;
            case "4": loadLevelFour();
                break;
            default:
                loadLevelOne();
        }

        // Read config from XML
        try {
            readConfig();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
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

    public void readConfig() throws XmlPullParserException {
        try {
            InputStream is = getResources().openRawResource(R.raw.levels_configuration);
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setInput(is, null);

            int event = xpp.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event){
                    case XmlPullParser.START_TAG:{
                        String id = xpp.getAttributeValue(null,"id");
                        if (id != null && id.equals(level)){
                            nbMoves = Integer.valueOf(xpp.getAttributeValue(null,"nb_moves"));
                            targetScore = Integer.valueOf(xpp.getAttributeValue(null,"target_score"));
                            x = Integer.valueOf(xpp.getAttributeValue(null,"x"));
                            y = Integer.valueOf(xpp.getAttributeValue(null,"y"));
                            return;
                        }
                    }
                        break;
                    default:
                        break;
                }
                event = xpp.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quit_handler(View view) {
        finish();
    }

    public void joker_handler(View view) {
        // TODO
    }

    public void initializeViews() {
        // Get views
        scoreTV = (TextView) findViewById(R.id.edit_score);
        movesLeftTV = (TextView) findViewById(R.id.edit_moves_left);
        targetScoreTV = (TextView) findViewById(R.id.edit_target);

        scoreTV.setText(String.valueOf(totalScore));
        movesLeftTV.setText(String.valueOf(nbMoves));
        targetScoreTV.setText(String.valueOf(targetScore));
    }

    public void updateViews() {
        scoreTV.setText(String.valueOf(totalScore));
        movesLeftTV.setText(String.valueOf(nbMoves));
    }

    public void calculateScore(int matchCount) {
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
        nbMoves--;

        if (totalScore >= targetScore) {
            // Load next level
            loadNextLevel();
        }
        else if (nbMoves == 0) {
            // Game over
            gameOver();
        }

        // Update score and moves views
        updateViews();
    }

    public void gameOver() {
        finish();
    }

    public void loadNextLevel() {
        Intent i = new Intent(this, GameActivity.class);
        Button btn = new Button(this);
        int nextLevel = Integer.valueOf(level) + 1;
        if (nextLevel < 4){
            i.putExtra("level",String.valueOf(nextLevel));
            switch (nextLevel) {
                case 2:
                    btn = (Button)findViewById(R.id.btn_level2);

                    break;
                case 3 :
                    btn = (Button)findViewById(R.id.btn_level3);
                    break;
                case 4 :
                    btn = (Button)findViewById(R.id.btn_level4);
                    break;
            }

            btn.setEnabled(true);
            startActivity(i);
        }
        else {
            Toast.makeText(this, "congratulations!", Toast.LENGTH_SHORT);
        }
    }
}
