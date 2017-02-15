package com.inf8405.match3app.match3app;

import java.io.Serializable;

/**
 * Created by habenah on 2017-02-13.
 */

class LevelConfig implements Serializable {
    public int level;
    public int initialNbMoves;
    public int targetScore;
    public int x;
    public int y;

    public LevelConfig() {
        this.level = 1;
        this.initialNbMoves = 0;
        this.targetScore = 0;
        this.x = 0;
        this.y = 0;
    }
}
