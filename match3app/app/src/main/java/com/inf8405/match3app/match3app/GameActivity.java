package com.inf8405.match3app.match3app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.GridView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    public String level;
    public int nbMoves;
    public int victoryPoints;
    public int x;
    public int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get selected level
        Intent i = getIntent();
        level = i.getStringExtra("level");

        // Read config from XML
        try {
            readConfig();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // Create the game view
        setContentView(R.layout.activity_game);
    }

    public void readConfig() throws XmlPullParserException {
        AssetManager am = getAssets();
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
                            nbMoves = Integer.valueOf(xpp.getAttributeValue(null,"nbMoves"));
                            victoryPoints = Integer.valueOf(xpp.getAttributeValue(null,"victoryPoints"));
                            x = Integer.valueOf(xpp.getAttributeValue(null,"x"));
                            y = Integer.valueOf(xpp.getAttributeValue(null,"y"));
                            Log.d("nbMoves = ", String.valueOf(nbMoves));
                            Log.d("victoryPoints = ", String.valueOf(victoryPoints));

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
}
