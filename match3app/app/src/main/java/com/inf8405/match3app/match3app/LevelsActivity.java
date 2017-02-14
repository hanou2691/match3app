package com.inf8405.match3app.match3app;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class LevelsActivity extends AppCompatActivity {

    private static InputStream xmlIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        xmlIS = getResources().openRawResource(R.raw.levels_configuration);
        setLockedState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        xmlIS = getResources().openRawResource(R.raw.levels_configuration);
        setLockedState();
    }

    void loadLevel(LevelConfig levelConfig){
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("level_config", levelConfig);
        startActivity(i);
    }


    public void level1_listener(View view) throws XmlPullParserException {
        LevelConfig levelConfig = readLevelConfig(xmlIS, 1);

        if (levelConfig != null)
            loadLevel(levelConfig);
    }

    public void level2_listener(View view) throws XmlPullParserException {
        LevelConfig levelConfig = readLevelConfig(xmlIS, 2);

        if (levelConfig != null)
            loadLevel(levelConfig);
    }

    public void level3_listener(View view) throws XmlPullParserException {
        LevelConfig levelConfig = readLevelConfig(xmlIS, 3);

        if (levelConfig != null)
            loadLevel(levelConfig);
    }

    public void level4_listener(View view) throws XmlPullParserException {
        LevelConfig levelConfig = readLevelConfig(xmlIS, 4);

        if (levelConfig != null)
            loadLevel(levelConfig);
    }

    @Nullable
    public static LevelConfig readLevelConfig(InputStream is, int level) throws XmlPullParserException {
        try {
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setInput(is, null);

            int event = xpp.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event){
                    case XmlPullParser.START_TAG:{
                        if (xpp.getName().equals("level")){
                            int id = Integer.valueOf(xpp.getAttributeValue(null,"id"));
                            if (id == level){
                                LevelConfig levelConfig = new LevelConfig();
                                levelConfig.level = level;
                                levelConfig.initialNbMoves = Integer.valueOf(xpp.getAttributeValue(null,"nb_moves"));
                                levelConfig.targetScore = Integer.valueOf(xpp.getAttributeValue(null,"target_score"));
                                levelConfig.x = Integer.valueOf(xpp.getAttributeValue(null,"x"));
                                levelConfig.y = Integer.valueOf(xpp.getAttributeValue(null,"y"));
                                return levelConfig;
                            }
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

        return null;
    }

    public void setLockedState() {
        boolean[] locks = ((Globals)getApplication()).locks;

        Button btn = (Button) findViewById(R.id.btn_level2);
        btn.setEnabled(!locks[0]);

        btn = (Button) findViewById(R.id.btn_level3);
        btn.setEnabled(!locks[1]);

        btn = (Button) findViewById(R.id.btn_level4);
        btn.setEnabled(!locks[2]);
    }
}
