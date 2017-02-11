package com.inf8405.match3app.match3app;

/**
 * Created by habenah on 2017-02-11.
 */

public class ComboThread extends Thread {
    public ComboThread(String name){
        super(name);
    }

    @Override
    public void run() {
        super.run();

        // Check for combo chains
        for (int i = 0; i < GameView.dim_y ; i++) {
            for (int j = 0; j < GameView.dim_x; j++) {
                if(GameView.findMatchHorizontal(j,i) || GameView.findMatchVertical(j,i)) {

                }
            }
        }
    }
}
